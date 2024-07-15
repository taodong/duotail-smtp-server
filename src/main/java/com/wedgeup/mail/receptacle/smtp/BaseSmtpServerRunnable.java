package com.wedgeup.mail.receptacle.smtp;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * ServerThread accepts TCP connections to the server socket and starts a new
 * {@link Session} thread for each connection which will handle the connection.
 * On shutdown it terminates not only this thread, but the session threads too.
 */
@Slf4j
class BaseSmtpServerRunnable implements Runnable {
	
	/**
	 * set a hard limit on the maximum number of connections this server will accept
	 * once we reach this limit, the server will gracefully reject new connections.
	 * Default is 1000.
	 */
	private static final int MAX_CONNECTIONS = 1000;

	private final BaseSmtpServer server;

	private final ServerSocket serverSocket;

	private final ExecutorService executorService;

	/**
	 * A semaphore which is used to prevent accepting new connections by blocking
	 * this thread if the allowed count of open connections is already reached.
	 */
	private final Semaphore connectionPermits;

	/**
	 * The list of currently running sessions.
	 */
	private final Set<Session> sessionThreads;

	/**
	 * A flag which indicates that this SMTP port and all of its open connections
	 * are being shut down.
	 */
	private volatile boolean shuttingDown;

	public BaseSmtpServerRunnable(final BaseSmtpServer server, final ServerSocket serverSocket) {
		this.server = server;
		this.serverSocket = serverSocket;
		// reserve a few places for graceful disconnects with informative
		// messages
		final int countOfConnectionPermits = MAX_CONNECTIONS + 10;
		this.connectionPermits = new Semaphore(countOfConnectionPermits);
		this.sessionThreads = new HashSet<>(countOfConnectionPermits * 4 / 3 + 1);
		this.executorService = server.isVirtualThreadsEnabled() ? Executors.newVirtualThreadPerTaskExecutor() : Executors.newCachedThreadPool();
	}

	/**
	 * This method is called by this thread when it starts up. To safely cause this
	 * to exit, call {@link #shutdown()}.
	 */
	@Override
	public void run() {
		MDC.put("smtpServerLocalSocketAddress", server.getDisplayableLocalSocketAddress());
		LOG.info("SMTP server {} started", server.getDisplayableLocalSocketAddress());

		try {
			runAcceptLoop();
			LOG.info("SMTP server {} stopped accepting connections", server.getDisplayableLocalSocketAddress());
		} finally {
			MDC.remove("smtpServerLocalSocketAddress");
		}
	}

	/**
	 * Accept connections and run them in session threads until shutdown.
	 */
	private void runAcceptLoop() {
		while (!this.shuttingDown) {
			onServerLoop();
		}
	}

	private void onServerLoop() {
		acquireConnectionPermit();

		acceptServerSocket(this.serverSocket)
				.flatMap(this::establishSocketSession)
				.map(this::addSessionsSynchronizedToActiveList)
				.ifPresent(this::executeSocketSession);
	}

	private void acquireConnectionPermit() {
		try {
			// block if too many connections are open
			connectionPermits.acquire();
		} catch (final InterruptedException consumed) {
			Thread.currentThread().interrupt();
		}
	}

	private Optional<Socket> acceptServerSocket(ServerSocket serverSocket){
		try {
			return Optional.of(serverSocket.accept());
		} catch (final IOException e) {
			connectionPermits.release();
			// it also happens during shutdown, when the socket is closed
			if (!this.shuttingDown) {
				LOG.error("Error accepting connection", e);
				// prevent a possible loop causing 100% processor usage
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException consumed) {
					Thread.currentThread().interrupt();
				}
			}
			return Optional.empty();
		}
	}

	private Optional<SocketSession> establishSocketSession(Socket socket){
		try {
			var session = new Session(server, this, socket);
			return Optional.of(new SocketSession(socket, session));
		} catch (final IOException e) {
			connectionPermits.release();
			LOG.error("Error while starting a connection", e);
			try {
				socket.close();
			} catch (final IOException e1) {
				LOG.debug("Cannot close socket after exception", e1);
			}
			return Optional.empty();
		}
	}

	private SocketSession addSessionsSynchronizedToActiveList(SocketSession socketSession) {
		// add thread before starting it,
		// because it will check the count of sessions
		synchronized (this) {
			this.sessionThreads.add(socketSession.session);
		}
		return socketSession;
	}

	private void executeSocketSession(SocketSession socketSession){
		try {
			executorService.execute(socketSession.session);
		} catch (final RejectedExecutionException e) {
			connectionPermits.release();
			synchronized (this) {
				this.sessionThreads.remove(socketSession.session);
			}
			LOG.error("Error while executing a session", e);
			try {
				socketSession.socket.close();
			} catch (final IOException e1) {
				LOG.debug("Cannot close socket after exception", e1);
			}
		}
	}

	/**
	 * Closes the server socket and all client sockets.
	 */
	public void shutdown() {
		// First make sure we aren't accepting any new connections
		shutdownServerThread();
		// Shut down any open connections.
		shutdownSessions();
	}

	private void shutdownServerThread() {
		shuttingDown = true;
		closeServerSocket();
	}

	/**
	 * Closes the serverSocket in an orderly way.
	 */
	private void closeServerSocket() {
		try {
			this.serverSocket.close();
			LOG.debug("SMTP Server socket shut down");
		} catch (final IOException e) {
			LOG.error("Failed to close server socket.", e);
		}
	}

	private void shutdownSessions() {
		// Copy the sessionThreads collection so the guarding lock on this
		// instance can be released before calling the Session.shutdown methods.
		// This is necessary to avoid a deadlock, because the terminating
		// session threads call back the sessionEnded function in this instance,
		// which locks this instance.
		List<Session> sessionsToBeClosed;
		synchronized (this) {
			sessionsToBeClosed = new ArrayList<>(sessionThreads);
		}
		for (final Session sessionThread : sessionsToBeClosed) {
			sessionThread.quit();
		}

		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (final InterruptedException e) {
			LOG.warn("Interrupted waiting for termination of session threads", e);
			Thread.currentThread().interrupt();
		}
	}

	public synchronized boolean hasTooManyConnections() {
		return sessionThreads.size() > MAX_CONNECTIONS;
	}

	public synchronized int getNumberOfConnections() {
		return sessionThreads.size();
	}

	/**
	 * Registers that the specified {@link Session} thread ended. Session threads
	 * must call this function.
	 */
	public void sessionEnded(final Session session) {
		synchronized (this) {
			sessionThreads.remove(session);
		}
		connectionPermits.release();
	}

	private record SocketSession(Socket socket, Session session) {
	}
}
