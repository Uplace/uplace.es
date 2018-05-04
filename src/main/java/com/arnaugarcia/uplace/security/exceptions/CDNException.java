package com.arnaugarcia.uplace.security.exceptions;

public class CDNException extends RuntimeException {
    private static final long serialVersionUID = -8770181313637973392L;

    public CDNException(String message) {
        super(message);
    }

    public CDNException(Throwable t) {
        super(t);
    }


}
