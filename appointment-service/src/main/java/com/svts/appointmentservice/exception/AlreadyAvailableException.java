package com.svts.appointmentservice.exception;

public class AlreadyAvailableException extends RuntimeException {

    public AlreadyAvailableException(String message) {
        super(message);
    }
}
