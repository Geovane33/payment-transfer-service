package com.purebank.paymenttransferservice.message.consumer;

import com.purebank.paymenttransferservice.transfer.domain.Transfer;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferMessageConsumer {
    @Autowired
    TransferService transferService;

    @RabbitListener(queues = "update-status-transfer")
    public void updateTransferStatus(Transfer transfer) {
//        transferRepository.save(transfer);

    }
}
