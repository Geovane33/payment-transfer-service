package com.purebank.paymenttransferservice.transfer.message.consumer;

import com.purebank.paymenttransferservice.exceptions.TransferException;
import com.purebank.paymenttransferservice.exceptions.handler.TransferUpdateWalletsException;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class TransferMessageConsumer {
    private static final String PERFORM_TRANSFER = "perform-transfer";
    @Autowired
    TransferService transferService;

    @RabbitListener(queues = PERFORM_TRANSFER)
    public void performTransfer(TransferResource transferResource) throws IOException {
        try {
            transferService.performTransfer(transferResource);
        } catch (TransferException ex) {
            log.error("{}", ex.getMessage());
        } catch (TransferUpdateWalletsException ex) {
            log.error("Falha ao atualizar carteiras: {}", ex.getMessage());
            log.error("Enviando dados para fila responsável por reverter operações: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Falha ao realizar transferência: {}", ex.getMessage());
            log.error("Enviando dados para fila responsável por reverter operações: {}", ex.getMessage());
        }
    }

}
