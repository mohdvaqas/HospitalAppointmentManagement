package com.svts.apigateway.exception;

public class AlreadyAvailableException extends RuntimeException {

    public AlreadyAvailableException(String message) {
        super(message);
    }
}
