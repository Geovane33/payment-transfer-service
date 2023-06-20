package com.purebank.paymenttransferservice.exceptions.handler;
public class TransferUpdateWalletsException extends RuntimeException {

    public TransferUpdateWalletsException(String message) {
        super(message);
    }

    public TransferUpdateWalletsException(String message, Throwable cause) {
        super(message, cause);
    }
}
