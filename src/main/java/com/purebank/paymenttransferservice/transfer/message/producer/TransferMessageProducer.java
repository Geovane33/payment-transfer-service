package com.purebank.paymenttransferservice.transfer.message.producer;

import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.common.resource.WalletActivityResource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferMessageProducer {

    private static final String DIRECT_EXCHANGE_DEFAULT = "direct-exchange-default";
    private static final String QUEUE_PERFORM_TRANSFER_KEY = "queue-perform-transfer-key";
    private static final String QUEUE_WALLET_ACTIVITY_KEY = "queue-wallet-activity-key";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendPerformTransferQueue(TransferResource transferResource) {
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_DEFAULT, QUEUE_PERFORM_TRANSFER_KEY, transferResource);
    }

    public void sendWalletActivity(WalletActivityResource walletActivityResource) {
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_DEFAULT, QUEUE_WALLET_ACTIVITY_KEY, walletActivityResource);
    }
}
