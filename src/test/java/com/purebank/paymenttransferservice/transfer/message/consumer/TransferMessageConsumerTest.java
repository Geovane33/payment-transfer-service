package com.purebank.paymenttransferservice.transfer.message.consumer;

import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TransferMessageConsumerTest {
    @Mock
    private TransferService transferService;

    @Mock
    private Channel channel;

    @InjectMocks
    private TransferMessageConsumer transferMessageConsumer;

    @Captor
    private ArgumentCaptor<Long> deliveryTagCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Teste fila de transferência")
    public void testPerformTransfer_Success() throws Exception {
        TransferResource transferResource = new TransferResource();

        transferMessageConsumer.performTransfer(transferResource);

        verify(transferService, times(1)).performTransfer(transferResource);
    }
}
