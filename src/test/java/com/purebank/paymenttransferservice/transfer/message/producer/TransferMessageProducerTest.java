package com.purebank.paymenttransferservice.transfer.message.producer;

import com.purebank.paymenttransferservice.common.resource.WalletActivityResource;
import com.purebank.paymenttransferservice.transfer.message.producer.TransferMessageProducer;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;

public class TransferMessageProducerTest {
    @Mock
    private RabbitTemplate rabbitTemplate;

    @Captor
    private ArgumentCaptor<Object> messageCaptor;

    @InjectMocks
    private TransferMessageProducer walletMessageProducer;

    private static final String DIRECT_EXCHANGE_DEFAULT = "direct-exchange-default";
    private static final String QUEUE_PERFORM_TRANSFER_KEY = "queue-perform-transfer-key";
    private static final String QUEUE_WALLET_ACTIVITY_KEY = "queue-wallet-activity-key";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendPerformTransferQueueTest() {
        TransferResource transferResource = new TransferResource();
        transferResource.setAmount(BigDecimal.valueOf(100));
        transferResource.setWalletOrigin(1L);
        transferResource.setWalletDestiny(2L);

        walletMessageProducer.sendPerformTransferQueue(transferResource);

        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.eq(DIRECT_EXCHANGE_DEFAULT),
                Mockito.eq(QUEUE_PERFORM_TRANSFER_KEY), messageCaptor.capture());

        Object capturedMessage = messageCaptor.getValue();
        Assertions.assertEquals(transferResource, capturedMessage);
    }

    @Test
    void sendWalletActivityTest() {
        WalletActivityResource walletActivityResource = new WalletActivityResource();
        walletActivityResource.setWalletId(1L);
        walletActivityResource.setAmount(BigDecimal.valueOf(50));

        walletMessageProducer.sendWalletActivity(walletActivityResource);

        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.eq(DIRECT_EXCHANGE_DEFAULT),
                Mockito.eq(QUEUE_WALLET_ACTIVITY_KEY), messageCaptor.capture());

        Object capturedMessage = messageCaptor.getValue();
        Assertions.assertEquals(walletActivityResource, capturedMessage);
    }
}