package com.duotail.smtp.server.smtp;

/**
 * SessionIdFactory creates reasonable unique identifiers which are applicable
 * to identify a session in the log files.
 */
public interface SessionIdFactory {
	/**
	 * Returns a new identifier.
	 *
	 * @return new identifier
	 */
	String create();
}
