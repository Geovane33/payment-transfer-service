package com.purebank.paymenttransferservice.transfer.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purebank.paymenttransferservice.transfer.api.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.domain.Transfer;
import com.purebank.paymenttransferservice.transfer.repository.TransferRepository;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import com.purebank.paymenttransferservice.transfer.utils.TransferStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransferServiceImpl implements TransferService {
    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private WalletService walletService;

    @Override
    public Long transfer(TransferResource transferResource) {
        Transfer transfer = new Transfer();
        transfer.setAmount(transferResource.getAmount());
        transfer.setStatus(TransferStatus.PENDING);
        transfer.setExternalAccount(transferResource.getExternalAccount());
        transfer.setWalletDestiny(transferResource.getWalletDestiny());
        transfer.setWalletOrigin(transferResource.getWalletOrigin());
        transferRepository.save(transfer);
        //Todo - Tratamento de exceção
        rabbitTemplate.convertAndSend("direct-exchange-default", "queue-update-accounts-balance-key", transfer);
        return transfer.getId();
    }

    @RabbitListener(queues = "update-status-transfer")
    public void updateTransferStatus(Transfer transfer) {
        transferRepository.save(transfer);
    }

    @Override
    public TransferResource getTransferById(Long walletId) {
        Transfer transferById = transferRepository.findTransferById(walletId);
        TransferResource transferResource = new TransferResource();
        transferResource.setAmount(transferById.getAmount());
        transferResource.setStatus(transferById.getStatus());
        transferResource.setStatusDescription(transferById.getStatusDescription());
        transferResource.setExternalAccount(transferById.isExternalAccount());
        transferResource.setWalletOrigin(transferById.getWalletOrigin());
        transferResource.setWalletDestiny(transferById.getWalletDestiny());
        return transferResource;
    }
}
