package com.purebank.paymenttransferservice.transfer.utils;

public enum TransferStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    CANCELED("Canceled");

    private String label;

    TransferStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
