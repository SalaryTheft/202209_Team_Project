package com.jotte.common.exception;

public class DaoDuplicateKeyException extends DaoException {

    public DaoDuplicateKeyException() {
        super();
    }

    public DaoDuplicateKeyException(String message) {
        super(message);
    }

    public DaoDuplicateKeyException(Throwable cause) {
        super(cause);
    }

    public DaoDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoDuplicateKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
