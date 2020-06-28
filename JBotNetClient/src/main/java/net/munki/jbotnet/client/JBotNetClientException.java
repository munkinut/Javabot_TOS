package net.munki.jbotnet.client;

public class JBotNetClientException extends Exception {

    public JBotNetClientException(String message) {
        super(message);
    }

    public JBotNetClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public JBotNetClientException(Throwable cause) {
        super(cause);
    }

    protected JBotNetClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

