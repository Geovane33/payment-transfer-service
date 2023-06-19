package com.purebank.paymenttransferservice.transfer;

import com.purebank.paymenttransferservice.transfer.api.TransferApi;
import com.purebank.paymenttransferservice.transfer.exceptions.Exception;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TransferController implements TransferApi {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TransferService transferService;

    @Override
    public ResponseEntity<String> transfer(TransferResource transferResource) {
        if (BigDecimal.ZERO.compareTo(transferResource.getAmount()) >= 0) {
            throw new Exception.InvalidAmount("Falha na transferencia: O valor tem que ser maior que R$0,00 e menor que R$1.000.000,00");
        }
        transferService.transfer(transferResource);
        return ResponseEntity.ok().build();
    }
}
