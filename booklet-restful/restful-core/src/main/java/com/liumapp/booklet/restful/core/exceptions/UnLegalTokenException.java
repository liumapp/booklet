package com.liumapp.booklet.restful.core.exceptions;

/**
 * file UnLegalTokenException.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/27
 */
public class UnLegalTokenException extends RuntimeException {

    private static final long serialVersionUID = 7524619428730680534L;

    public UnLegalTokenException() {
        super();
    }

    public UnLegalTokenException(String message) {
        super(message);
    }

    public UnLegalTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnLegalTokenException(Throwable cause) {
        super(cause);
    }

    protected UnLegalTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
