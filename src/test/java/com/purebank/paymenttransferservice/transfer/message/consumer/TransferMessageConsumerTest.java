package com.purebank.paymenttransferservice.transfer.message.consumer;

import com.purebank.paymenttransferservice.transfer.message.consumer.TransferMessageConsumer;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TransferMessageConsumerTest {
    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferMessageConsumer transferConsumer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void updateTransferStatusTest() {
        TransferResource transferResource = new TransferResource();

        transferConsumer.updateTransferStatus(transferResource);

        Mockito.verify(transferService).performTransfer(Mockito.eq(transferResource));
    }


}
