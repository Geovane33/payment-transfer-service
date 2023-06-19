package com.purebank.paymenttransferservice.transfer.message.consumer;

import com.purebank.paymenttransferservice.transfer.domain.Transfer;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferMessageConsumer {
    private static final String PERFORM_TRANSFER = "perform-transfer";
    @Autowired
    TransferService transferService;

    @RabbitListener(queues = PERFORM_TRANSFER)
    public void updateTransferStatus(TransferResource transferResource) {
        transferService.performTransfer(transferResource);
    }
}
