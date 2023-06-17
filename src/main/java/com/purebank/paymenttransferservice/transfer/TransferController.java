package com.purebank.paymenttransferservice.transfer;

import com.purebank.paymenttransferservice.transfer.api.TransferApi;
import com.purebank.paymenttransferservice.transfer.api.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController implements TransferApi {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TransferService transferService;

    @Override
    public ResponseEntity<TransferResource> transfer(TransferResource TransferResource) {
        return ResponseEntity.ok(TransferResource);
    }

    @Override
    public ResponseEntity<TransferResource> getTransferById(Long transferId) {
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> teste(String msg) {
        return null;
    }

}
