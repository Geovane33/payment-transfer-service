package com.purebank.paymenttransferservice.payment.service;

import com.purebank.paymenttransferservice.common.resource.WalletActivityResource;
import com.purebank.paymenttransferservice.exceptions.Exception;
import com.purebank.paymenttransferservice.external.wallet.resourcer.WalletResource;
import com.purebank.paymenttransferservice.external.wallet.service.WalletServiceFeignClient;
import com.purebank.paymenttransferservice.payment.message.producer.PaymentMessageProducer;
import com.purebank.paymenttransferservice.payment.service.Impl.PaymentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;

public class PaymentServiceImplTests {
    @Mock
    private WalletServiceFeignClient walletServiceFeignClient;

    @Mock
    private PaymentMessageProducer paymentMessageProducer;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Realizar pagamento com saldo suficiente")
    public void payWithSufficientBalanceTest() {
        Long walletId = 1L;
        String paymentIdentifier = "12345";

        WalletResource walletResource = new WalletResource();
        walletResource.setId(walletId);
        walletResource.setBalance(BigDecimal.valueOf(200.00));

        Mockito.when(walletServiceFeignClient.getWallet(ArgumentMatchers.anyLong())).thenReturn(walletResource);

        paymentService.pay(walletId, paymentIdentifier);

        Mockito.verify(walletServiceFeignClient, Mockito.times(1)).updateWallet(walletResource);
        Mockito.verify(paymentMessageProducer, Mockito.times(1)).sendWalletActivity(Mockito.any(WalletActivityResource.class));
    }

    @Test
    @DisplayName("Falha ao realizar pagamento por saldo insuficiente")
    public void payWithInsufficientBalanceTest() {
        Long walletId = 1L;
        String paymentIdentifier = "12345";

        WalletResource walletResource = new WalletResource();
        walletResource.setId(walletId);
        walletResource.setBalance(BigDecimal.valueOf(50.00));

        Mockito.when(walletServiceFeignClient.getWallet(ArgumentMatchers.anyLong())).thenReturn(walletResource);

        Exception exception = Assertions.assertThrows(Exception.InsufficientBalance.class, () -> {
            paymentService.pay(walletId, paymentIdentifier);
        });

        Assertions.assertEquals("Saldo insuficiente para realizar o pagamento da conta: 12345", exception.getMessage());

        Mockito.verify(walletServiceFeignClient, Mockito.never()).updateWallet(walletResource);
        Mockito.verify(paymentMessageProducer, Mockito.times(1)).sendWalletActivity(Mockito.any(WalletActivityResource.class));
    }
}
