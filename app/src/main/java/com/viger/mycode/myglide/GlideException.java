package com.viger.mycode.myglide;

public class GlideException extends RuntimeException {

    public GlideException() {
    }

    public GlideException(String message) {
        super(message);
    }

    public GlideException(String message, Throwable cause) {
        super(message, cause);
    }
}
