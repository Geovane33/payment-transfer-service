package com.purebank.paymenttransferservice.transfer.exceptions;
public class TransferException extends RuntimeException {

    public TransferException(String message) {
        super(message);
    }

    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }
}
