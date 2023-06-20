package com.purebank.paymenttransferservice.payment.message.producer;

import com.purebank.paymenttransferservice.common.enums.ActivityType;
import com.purebank.paymenttransferservice.common.enums.ProcessStatus;
import com.purebank.paymenttransferservice.common.resource.WalletActivityResource;
import com.purebank.paymenttransferservice.transfer.message.producer.TransferMessageProducer;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentMessageProducerTest {

    public static final String DIRECT_EXCHANGE_DEFAULT = "direct-exchange-default";
    public static final String QUEUE_WALLET_ACTIVITY_KEY = "queue-wallet-activity-key";
    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PaymentMessageProducer paymentMessageProducer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Enviar atividade de carteira")
    public void sendWalletActivity_Success() {
        WalletActivityResource walletActivityResource = new WalletActivityResource();
        walletActivityResource.setActivityDate(LocalDateTime.now());
        walletActivityResource.setActivityType(ActivityType.TRANSFER);
        walletActivityResource.setDescription("Descrição da atividade");
        walletActivityResource.setStatus(ProcessStatus.COMPLETED);
        walletActivityResource.setAmount(BigDecimal.valueOf(100));
        walletActivityResource.setWalletId(1L);
        walletActivityResource.setCreationDate(LocalDateTime.now());
        walletActivityResource.setUuidActivity(UUID.randomUUID().toString());

        paymentMessageProducer.sendWalletActivity(walletActivityResource);

        Mockito.verify(rabbitTemplate, Mockito.times(1))
                .convertAndSend(DIRECT_EXCHANGE_DEFAULT, QUEUE_WALLET_ACTIVITY_KEY, walletActivityResource);
    }

}