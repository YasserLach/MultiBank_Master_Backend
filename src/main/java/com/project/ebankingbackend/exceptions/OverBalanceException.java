package com.project.ebankingbackend.exceptions;

public class OverBalanceException extends RuntimeException{
    public OverBalanceException() {
        super();
    }

    public OverBalanceException(String message) {
        super(message);
    }

    public OverBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverBalanceException(Throwable cause) {
        super(cause);
    }

    public OverBalanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
