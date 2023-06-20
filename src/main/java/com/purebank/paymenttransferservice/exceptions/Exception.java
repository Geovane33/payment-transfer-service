package com.purebank.paymenttransferservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class Exception extends RuntimeException {

    @Getter
    @Setter
    private HttpStatus status;

    public static class NotFound extends Exception {
        public NotFound(String message) {
            super(message, HttpStatus.NOT_FOUND);
        }
    }

    public static class InsufficientBalance extends Exception {
        public InsufficientBalance(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static class InvalidAmount extends Exception {
        public InvalidAmount(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static class ServiceTemporarilyOffline extends Exception {
        public ServiceTemporarilyOffline(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
    }

    public Exception(String message, HttpStatus... status) {
        super(message);
        this.status = HttpStatus.OK;
        if (status.length > 0)
            this.status = status[0];
    }

    public Exception(HttpStatus status) {
        super(status.getReasonPhrase());
        this.status = status;
    }
}
