package com.purebank.paymenttransferservice.transfer.service.Impl;

import com.purebank.paymenttransferservice.common.enums.ActivityType;
import com.purebank.paymenttransferservice.exceptions.handler.TransferUpdateWalletsException;
import com.purebank.paymenttransferservice.external.wallet.resourcer.WalletResource;
import com.purebank.paymenttransferservice.external.wallet.service.WalletServiceFeignClient;
import com.purebank.paymenttransferservice.transfer.message.producer.TransferMessageProducer;
import com.purebank.paymenttransferservice.common.enums.ProcessStatus;
import com.purebank.paymenttransferservice.exceptions.Exception;
import com.purebank.paymenttransferservice.exceptions.TransferException;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.common.resource.WalletActivityResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.purebank.paymenttransferservice.transfer.utils.constants.Constants.*;

@Service
@Slf4j
public class TransferServiceImpl implements TransferService {
    @Autowired
    private WalletServiceFeignClient walletServiceFeignClient;

    @Autowired
    private TransferMessageProducer transferMessageProducer;

    @Override
    public void transfer(TransferResource transferResource) {
        WalletResource walletOrigin = getWalletResource(transferResource
                .getWalletOrigin(), new Exception.NotFound(String
                .format(FAILURE_TRANSFER_ORIGIN_WALLET_NOT_FOUND, transferResource.getWalletOrigin())));

        WalletResource walletDestiny = getWalletResource(transferResource
                .getWalletDestiny(), new Exception.NotFound(String
                .format(FAILURE_TRANSFER_DESTINATION_WALLET_NOT_FOUND, transferResource.getWalletDestiny())));

        if (insufficientBalance(transferResource, walletOrigin)) {
            throw new Exception.InsufficientBalance(FAILURE_TRANSFER_INSUFFICIENT_BALANCE);
        }

        transferResource.setUuidActivity(UUID.randomUUID().toString());
        sendWalletActivity(String.format(TRANSFER_TO_WALLET_X, walletDestiny.getName()), walletOrigin.getId(), ProcessStatus.PENDING, transferResource);
        transferMessageProducer.sendPerformTransferQueue(transferResource);
    }

    public void performTransfer(TransferResource transferResource) {
        WalletResource walletOrigin = getWalletResource(transferResource
                .getWalletOrigin(), new TransferException(String
                .format(FAILURE_TRANSFER_ORIGIN_WALLET_NOT_FOUND, transferResource.getWalletOrigin())));

        WalletResource walletDestiny = getWalletResource(transferResource
                .getWalletDestiny(), new TransferException(String
                .format(FAILURE_TRANSFER_DESTINATION_WALLET_NOT_FOUND, transferResource.getWalletDestiny())));

        if (insufficientBalance(transferResource, walletOrigin)) {
            sendWalletActivity(String.format(FAILURE_TRANSFER_TO_WALLET_X_INSUFFICIENT_BALANCE, walletDestiny.getName()), walletOrigin.getId(), ProcessStatus.FAILED, transferResource);
            throw new TransferException(FAILURE_TRANSFER_INSUFFICIENT_BALANCE);
        }

        updateAccountsBalance(transferResource, walletOrigin, walletDestiny);

        sendWalletActivity(String.format(TRANSFER_TO_WALLET_X, walletDestiny.getName()), walletOrigin.getId(), ProcessStatus.COMPLETED, transferResource);

        sendWalletActivity(String.format(TRANSFER_RECEIVED_FROM_WALLET_X, walletOrigin.getName()), walletDestiny.getId(), ProcessStatus.COMPLETED, transferResource);
    }

    private WalletResource getWalletResource(Long transferResource, RuntimeException ex) {
        try {
            return walletServiceFeignClient.getWallet(transferResource);
        } catch (FeignException.NotFound notFound) {
            throw ex;
        } catch (FeignException e) {
            log.error("Erro ao obter carteira : {}", e.getMessage());
            throw new Exception.ServiceTemporarilyOffline("Serviço temporariamente fora do ar");
        }
    }

    private void sendWalletActivity(String description, Long walletId, ProcessStatus status, TransferResource transferResource) {
        WalletActivityResource walletActivityResource = new WalletActivityResource();
        walletActivityResource.setActivityDate(LocalDateTime.now());
        walletActivityResource.setActivityType(ActivityType.TRANSFER);
        walletActivityResource.setDescription(description);
        walletActivityResource.setStatus(status);
        walletActivityResource.setAmount(transferResource.getAmount());
        walletActivityResource.setWalletId(walletId);
        walletActivityResource.setCreationDate(LocalDateTime.now());
        walletActivityResource.setUuidActivity(transferResource.getUuidActivity());
        transferMessageProducer.sendWalletActivity(walletActivityResource);
    }

    private void updateAccountsBalance(TransferResource transferResource, WalletResource walletOrigin, WalletResource walletDestiny) {
        try {
            walletDestiny.setBalance(walletDestiny.getBalance().add(transferResource.getAmount()));
            walletOrigin.setBalance(walletOrigin.getBalance().subtract(transferResource.getAmount()));
            walletServiceFeignClient.updateWallet(walletOrigin);
            walletServiceFeignClient.updateWallet(walletDestiny);
        } catch (TransferUpdateWalletsException ex) {
            log.error("Erro ao atualizar saldo de contas durante tranferência conta origem: {} - conta destino: {}", walletOrigin.getId(), walletDestiny.getId());
            sendWalletActivity(String.format(FAILURE_TRANSFER_TO_WALLET_X, walletDestiny.getName()), walletOrigin.getId(), ProcessStatus.FAILED, transferResource);
            throw ex;
        }

    }

    private boolean insufficientBalance(TransferResource transferResource, WalletResource walletOrigin) {
        return (walletOrigin.getBalance().compareTo(transferResource.getAmount()) < 0);
    }
}
