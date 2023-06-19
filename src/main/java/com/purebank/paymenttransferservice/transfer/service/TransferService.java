package com.purebank.paymenttransferservice.transfer.service;

import com.purebank.paymenttransferservice.transfer.resource.TransferResource;


public interface TransferService {
    void transfer(TransferResource transferResource);

    void performTransfer(TransferResource transferResource);
}
