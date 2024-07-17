package com.duotail.smtp.server.smtp.command;

import com.duotail.smtp.server.smtp.RejectException;
import com.duotail.smtp.server.smtp.Session;
import com.duotail.smtp.server.smtp.io.DotTerminatedInputStream;
import com.duotail.smtp.server.smtp.io.DotUnstuffingInputStream;
import com.duotail.smtp.server.smtp.io.ReceivedHeaderStream;

import java.io.BufferedInputStream;
import java.io.IOException;

public class DataCommand extends BaseCommand {
    private static final int BUFFER_SIZE = 1024 * 32; // 32k seems reasonable

    public DataCommand() {
        super(CommandVerb.DATA, "Following text is collected as the message.\n" + "End data with <CR><LF>.<CR><LF>");
    }

    @Override
    public void execute(final String commandString, final Session sess) throws IOException {
        if (!sess.isMailTransactionInProgress()) {
            sess.sendResponse("503 5.5.1 Error: need MAIL command");
            return;
        }
        if (sess.getRecipientCount() == 0) {
            sess.sendResponse("503 Error: need RCPT command");
            return;
        }

        sess.sendResponse("354 End data with <CR><LF>.<CR><LF>");

        final var rhs = buildReceivedHeaderStream(sess);
        try {
            sess.getMessageHandler().data(rhs);
            while (rhs.read() != -1) {
                // Just in case the handler didn't consume all the data, we might as well
                // suck it up so it doesn't pollute further exchanges.  This code used to
                // throw an exception, but this seems an arbitrary part of the contract that
                // we might as well relax.
            }
        } catch (final RejectException ex) {
            sess.sendResponse(ex.getErrorResponse());
            return;
        }

        sess.sendResponse("250 Ok");
        sess.resetMailTransaction();
    }

    private static ReceivedHeaderStream buildReceivedHeaderStream(Session sess) {
        final var bis = new BufferedInputStream(sess.getRawInput(), BUFFER_SIZE);
        final var btis = new DotTerminatedInputStream(bis);
        final var duis = new DotUnstuffingInputStream(btis);
        return new ReceivedHeaderStream(duis,
                sess.getHelo(),
                sess.getRemoteAddress().getAddress(),
                sess.getServer().getHostName(),
                sess.getServer().getSoftwareName(),
                sess.getSessionId(),
                sess.getSingleRecipient());
    }
}
