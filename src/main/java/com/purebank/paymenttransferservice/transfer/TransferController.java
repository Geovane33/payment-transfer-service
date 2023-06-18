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
    public ResponseEntity<String> transfer(TransferResource TransferResource) {
        Long transferId = transferService.transfer(TransferResource);
        String urlStatusTransfer = String.format("/api/transfer/%s", transferId);
        return ResponseEntity.ok(urlStatusTransfer);
    }

    @Override
    public ResponseEntity<TransferResource> getTransferById(Long transferId) {
        TransferResource transferById = transferService.getTransferById(transferId);
        return ResponseEntity.ok(transferById);
    }

    public ResponseEntity<Void> teste(String msg) {
        return null;
    }

}
