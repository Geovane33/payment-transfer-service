package com.purebank.paymenttransferservice.message.producer;

import com.purebank.paymenttransferservice.transfer.api.resource.TransferResource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendUpdate(TransferResource transferResource){
        rabbitTemplate.convertAndSend("direct-exchange-default", "queue-update-accounts-balance-key", transferResource);
    }
}
