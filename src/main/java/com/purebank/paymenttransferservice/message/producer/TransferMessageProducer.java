package com.purebank.paymenttransferservice.message.producer;

import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.resource.WalletActivityResource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendPerformTransferQueue(TransferResource transferResource){
        rabbitTemplate.convertAndSend("direct-exchange-default", "queue-perform-transfer-key", transferResource);
    }

    public void sendWalletActivity(WalletActivityResource walletActivityResource){
        rabbitTemplate.convertAndSend("direct-exchange-default", "queue-wallet-activity-key", walletActivityResource);
    }
}
