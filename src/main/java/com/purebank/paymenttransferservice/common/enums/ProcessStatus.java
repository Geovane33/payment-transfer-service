package com.purebank.paymenttransferservice.common.enums;

public enum ProcessStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    FAILED("Failed");
    private String label;

    ProcessStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
