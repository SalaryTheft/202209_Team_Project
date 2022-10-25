package com.jotte.common.exception;

public class BizDuplicateKeyException extends BizException {

    public BizDuplicateKeyException() {
        super();
    }

    public BizDuplicateKeyException(String message) {
        super(message);
    }

    public BizDuplicateKeyException(Throwable cause) {
        super(cause);
    }

    public BizDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizDuplicateKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
