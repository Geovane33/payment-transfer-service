package com.purebank.paymenttransferservice.payment;

import com.purebank.paymenttransferservice.payment.api.PaymentApi;
import com.purebank.paymenttransferservice.payment.service.PaymentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController implements PaymentApi {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private PaymentService paymentService;

    public ResponseEntity<String> pay(Long walletId, Long paymentIdentifier) {
        paymentService.pay(walletId, paymentIdentifier);
        return ResponseEntity.ok().build();
    }
}
