package com.svts.appointmentservice.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message ) {
        super(message);
    }
}
