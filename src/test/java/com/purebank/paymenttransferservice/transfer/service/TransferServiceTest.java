package com.purebank.paymenttransferservice.transfer.service;

import com.purebank.paymenttransferservice.common.resource.WalletActivityResource;
import com.purebank.paymenttransferservice.exceptions.Exception;
import com.purebank.paymenttransferservice.external.wallet.resourcer.WalletResource;
import com.purebank.paymenttransferservice.external.wallet.service.WalletServiceFeignClient;
import com.purebank.paymenttransferservice.transfer.message.producer.TransferMessageProducer;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.Impl.TransferServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class TransferServiceTest {
    @Mock
    private WalletServiceFeignClient walletServiceFeignClient;

    @Mock
    private TransferMessageProducer transferMessageProducer;

    @InjectMocks
    private TransferServiceImpl transferService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Enviar transferência para fila de processamento")
    public void transfer_ValidTransferResource() {
        TransferResource transferResource = new TransferResource();
        transferResource.setWalletOrigin(1L);
        transferResource.setWalletDestiny(2L);
        transferResource.setAmount(BigDecimal.valueOf(100));
        transferResource.setExternalAccount(false);

        WalletResource walletOrigin = new WalletResource();
        walletOrigin.setId(1L);
        walletOrigin.setBalance(BigDecimal.valueOf(500));

        WalletResource walletDestiny = new WalletResource();
        walletDestiny.setId(2L);
        walletDestiny.setBalance(BigDecimal.valueOf(200));

        Mockito.when(walletServiceFeignClient.getWallet(1L)).thenReturn(walletOrigin);
        Mockito.when(walletServiceFeignClient.getWallet(2L)).thenReturn(walletDestiny);

        transferService.transfer(transferResource);

        Mockito.verify(transferMessageProducer, Mockito.times(1)).sendPerformTransferQueue(transferResource);
        Mockito.verify(transferMessageProducer, Mockito.times(1)).sendWalletActivity(Mockito.any());
    }

    @Test
    @DisplayName("Realizar transferência com sucesso")
    public void performTransfer_ValidTransferResource() {
        TransferResource transferResource = new TransferResource();
        transferResource.setWalletOrigin(1L);
        transferResource.setWalletDestiny(2L);
        transferResource.setAmount(BigDecimal.valueOf(100));
        transferResource.setExternalAccount(false);

        WalletResource walletOrigin = new WalletResource();
        walletOrigin.setId(1L);
        walletOrigin.setBalance(BigDecimal.valueOf(500));

        WalletResource walletDestiny = new WalletResource();
        walletDestiny.setId(2L);
        walletDestiny.setBalance(BigDecimal.valueOf(200));

        Mockito.when(walletServiceFeignClient.getWallet(1L)).thenReturn(walletOrigin);
        Mockito.when(walletServiceFeignClient.getWallet(2L)).thenReturn(walletDestiny);

        transferService.performTransfer(transferResource);

        Mockito.verify(walletServiceFeignClient, Mockito.times(1)).updateWallet(walletOrigin);
        Mockito.verify(walletServiceFeignClient, Mockito.times(1)).updateWallet(walletDestiny);
        Mockito.verify(transferMessageProducer, Mockito.times(2)).sendWalletActivity(Mockito.any(WalletActivityResource.class));
    }

    @Test
    @DisplayName("Transferir com saldo insuficiente")
    public void transfer_InsufficientBalance_ThrowsInsufficientBalanceException()  {
        TransferResource transferResource = new TransferResource();
        transferResource.setWalletOrigin(1L);
        transferResource.setWalletDestiny(2L);
        transferResource.setAmount(BigDecimal.valueOf(1000));
        transferResource.setExternalAccount(false);

        WalletResource walletOrigin = new WalletResource();
        walletOrigin.setId(1L);
        walletOrigin.setBalance(BigDecimal.valueOf(500));

        WalletResource walletDestiny = new WalletResource();
        walletDestiny.setId(2L);
        walletDestiny.setBalance(BigDecimal.valueOf(200));

        Mockito.when(walletServiceFeignClient.getWallet(1L)).thenReturn(walletOrigin);
        Mockito.when(walletServiceFeignClient.getWallet(2L)).thenReturn(walletDestiny);

        Assertions.assertThrows(Exception.InsufficientBalance.class, () -> transferService.transfer(transferResource));
    }

    @Test
    @DisplayName("Transferir com carteira de origem inválida")
    public void transfer_InvalidWalletOrigin_ThrowsNotFoundException() {
        TransferResource transferResource = new TransferResource();
        transferResource.setWalletOrigin(1L);
        transferResource.setWalletDestiny(2L);
        transferResource.setAmount(BigDecimal.valueOf(100));
        transferResource.setExternalAccount(false);

        Mockito.when(walletServiceFeignClient.getWallet(1L)).thenThrow(FeignException.NotFound.class);

        Exception.NotFound notFound = Assertions.assertThrows(Exception.NotFound.class, () -> transferService.transfer(transferResource));
        Assertions.assertEquals("Falha na transferência: Carteira de origem não encontrada: 1", notFound.getMessage());
    }

    @Test
    @DisplayName("Tentar transferir com carteira de destino inválida")
    public void transfer_InvalidWalletDestiny_ThrowsNotFoundException() {
        TransferResource transferResource = new TransferResource();
        transferResource.setWalletOrigin(1L);
        transferResource.setWalletDestiny(2L);
        transferResource.setAmount(BigDecimal.valueOf(100));
        transferResource.setExternalAccount(false);

        WalletResource walletOrigin = new WalletResource();
        walletOrigin.setId(1L);
        walletOrigin.setBalance(BigDecimal.valueOf(500));

        Mockito.when(walletServiceFeignClient.getWallet(1L)).thenReturn(walletOrigin);
        Mockito.when(walletServiceFeignClient.getWallet(2L)).thenThrow(FeignException.NotFound.class);

        Exception.NotFound notFound = Assertions.assertThrows(Exception.NotFound.class, () -> transferService.transfer(transferResource));
        Assertions.assertEquals("Falha na transferência: Carteira de destino não encontrada: 2", notFound.getMessage());
    }
}
