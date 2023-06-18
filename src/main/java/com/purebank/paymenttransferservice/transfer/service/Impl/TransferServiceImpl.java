package com.purebank.paymenttransferservice.transfer.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.purebank.paymenttransferservice.transfer.api.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.domain.Transfer;
import com.purebank.paymenttransferservice.transfer.exceptions.TransferException;
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

    @Autowired
    private ObjectMapper objectMapper;


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
        String transferAsString;
        try {
            transferAsString = objectMapper.writeValueAsString(transfer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        rabbitTemplate.convertAndSend("direct-exchange-default", "queue-update-accounts-balance-key", transferAsString);
        return transfer.getId();
    }

    @RabbitListener(queues = "update-status-transfer")
    public void updateTransferStatus(String payload) {
        Transfer transfer;
        try {
            transfer = objectMapper.readValue(payload, Transfer.class);
        } catch (JsonProcessingException ex) {
            throw new TransferException("Erro ao realizar parse do payload de atualização do status da transação", ex);
        }
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
