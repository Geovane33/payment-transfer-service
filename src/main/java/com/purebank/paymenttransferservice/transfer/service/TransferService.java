package com.purebank.paymenttransferservice.transfer.service;

import com.purebank.paymenttransferservice.transfer.api.resource.TransferResource;


public interface TransferService {
    Long transfer(TransferResource transferResource);

    TransferResource getTransferById(Long walletId);
}
