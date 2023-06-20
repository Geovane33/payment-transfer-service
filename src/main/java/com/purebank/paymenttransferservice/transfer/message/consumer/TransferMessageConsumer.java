package com.purebank.paymenttransferservice.transfer.message.consumer;

import com.purebank.paymenttransferservice.exceptions.TransferException;
import com.purebank.paymenttransferservice.exceptions.handler.TransferUpdateWalletsException;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TransferMessageConsumer {
    private static final String PERFORM_TRANSFER = "perform-transfer";
    @Autowired
    TransferService transferService;

    @RabbitListener(queues = PERFORM_TRANSFER)
    public void performTransfer(TransferResource transferResource, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            transferService.performTransfer(transferResource);
            //Confirma o processamento bem-sucedido da mensagem
            confirmMessageProcessing(channel, deliveryTag);
        }catch (TransferException ex) {
            log.error("{}", ex.getMessage());
            confirmMessageProcessing(channel, deliveryTag);
        }catch (TransferUpdateWalletsException ex){
            log.error("Falhar ao atualizar carteiras: {}", ex.getMessage());
            log.error("Enviando dados para fila responsável por reverter operações: {}", ex.getMessage());
            confirmMessageProcessing(channel, deliveryTag);
        }catch (Exception ex) {
            //Verifica o número máximo de tentativas foi atingido
            int maxRetries = 3;
            int currentRetry = getRetryCount(channel, deliveryTag);

            if (currentRetry < maxRetries) {
                //Rejeita a mensagem e solicita que o RabbitMQ a reenfileire após um curto intervalo
                sendMessageToQueueAgain(channel, deliveryTag);
            } else {
                //Confirma o processamento da mensagem, mesmo que ocorra uma exceção após várias tentativas
                confirmMessageProcessing(channel, deliveryTag);
                //Loga o erro
                log.error("Erro ao realizar a transferência após múltiplas tentativas: {}", ex.getMessage());
            }
        }
    }

    private static void confirmMessageProcessing(Channel channel, long deliveryTag) throws IOException {
        channel.basicAck(deliveryTag, false);
    }

    private static void sendMessageToQueueAgain(Channel channel, long deliveryTag) throws IOException {
        channel.basicNack(deliveryTag, false, true);
    }

    private int getRetryCount(Channel channel, long deliveryTag) throws IOException {
        GetResponse response = channel.basicGet(String.valueOf(deliveryTag), false);
        // Verifica se a mensagem existe
        if (response != null) {
            // Obtém as informações de cabeçalho da mensagem
            Map<String, Object> headers = response.getProps().getHeaders();
            // Verifica se a contagem de re-tentativas existe no cabeçalho
            Integer death = checkRetryCountInHeader(headers);
            if (death != null) return death;
        }
        return 0;
    }

    private static Integer checkRetryCountInHeader(Map<String, Object> headers) {
        if (headers != null && headers.containsKey("x-death")) {
            List<Map<String, Object>> xDeath = (List<Map<String, Object>>) headers.get("x-death");
            // Obtém a contagem de re-tentativas
            if (xDeath != null && !xDeath.isEmpty()) {
                Map<String, Object> death = xDeath.get(0);
                return (int) death.get("count");
            }
        }
        return null;
    }
}
