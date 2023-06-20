package com.purebank.paymenttransferservice.payment.service.Impl;

import com.purebank.paymenttransferservice.common.enums.ActivityType;
import com.purebank.paymenttransferservice.common.enums.ProcessStatus;
import com.purebank.paymenttransferservice.common.resource.WalletActivityResource;
import com.purebank.paymenttransferservice.exceptions.Exception;
import com.purebank.paymenttransferservice.external.wallet.resourcer.WalletResource;
import com.purebank.paymenttransferservice.external.wallet.service.WalletServiceFeignClient;
import com.purebank.paymenttransferservice.payment.message.producer.PaymentMessageProducer;
import com.purebank.paymenttransferservice.payment.service.PaymentService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.purebank.paymenttransferservice.payment.utils.constants.Constants.*;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    public static final BigDecimal AMOUNT_PAYMENT = new BigDecimal("100.00");
    @Autowired
    private WalletServiceFeignClient walletServiceFeignClient;

    @Autowired
    private PaymentMessageProducer paymentMessageProducer;

    @Override
    public void pay(Long walletId, String paymentIdentifier) {
        WalletResource wallet = getWalletResource(walletId, new Exception.NotFound(String.format(WALLET_NOT_FOUND, walletId)));

        if (insufficientBalance(wallet)) {
            sendWalletActivity(String.format(INSUFFICIENT_BALANCE_TO_PERFORM_PAYMENT, paymentIdentifier),
                    walletId, ProcessStatus.FAILED);

            throw new Exception.InsufficientBalance(String.format(INSUFFICIENT_BALANCE_TO_PERFORM_PAYMENT, paymentIdentifier));
        }

        wallet.setBalance(wallet.getBalance().subtract(AMOUNT_PAYMENT));
        walletServiceFeignClient.updateWallet(wallet);

        sendWalletActivity(String.format(PAYMENT_SUCCESS_MESSAGE, paymentIdentifier), walletId, ProcessStatus.COMPLETED);
    }

    private void sendWalletActivity(String description, Long walletId, ProcessStatus status) {
        WalletActivityResource walletActivityResource = new WalletActivityResource();
        walletActivityResource.setDescription(description);
        walletActivityResource.setWalletId(walletId);
        walletActivityResource.setActivityType(ActivityType.PAYMENT);
        walletActivityResource.setStatus(status);
        walletActivityResource.setAmount(AMOUNT_PAYMENT);
        walletActivityResource.setActivityDate(LocalDateTime.now());
        walletActivityResource.setCreationDate(LocalDateTime.now());
        paymentMessageProducer.sendWalletActivity(walletActivityResource);
    }

    private WalletResource getWalletResource(Long walletId, RuntimeException ex) {
        try {
            return walletServiceFeignClient.getWallet(walletId);
        } catch (FeignException.NotFound notFound) {
            throw ex;
        } catch (Exception e) {
            log.error("Erro ao obter carteira : {}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    private boolean insufficientBalance(WalletResource wallet) {
        return (wallet.getBalance().compareTo(AMOUNT_PAYMENT) < 0);
    }
}
