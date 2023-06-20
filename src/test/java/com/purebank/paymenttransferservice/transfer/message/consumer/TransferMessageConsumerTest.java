package com.purebank.paymenttransferservice.transfer.message.consumer;

import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    @DisplayName("Teste de tranferêcia com sucesso")
    public void testPerformTransfer_Success() throws IOException {
        //Criar um objeto de transferência fictício
        TransferResource transferResource = new TransferResource();

        //Chamar o método de atualização do status de transferência
        transferMessageConsumer.performTransfer(transferResource, channel, 1L);

        //Verificar se o método performTransfer foi chamado uma vez
        verify(transferService, times(1)).performTransfer(transferResource);

        //Verificar se o método basicAck foi chamado com os parâmetros corretos
        verify(channel, times(1)).basicAck(anyLong(), eq(false));
    }

    @Test
    @DisplayName("Teste de tranferêcia com a primeira retentiva")
    public void testUpdateTransferStatus_Retry() throws IOException {
        //Criar um objeto de transferência fictício
        TransferResource transferResource = new TransferResource();

        //Criar um mapa de cabeçalhos simulando a contagem de re-tentativas
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-death", Collections.singletonList(Collections.singletonMap("count", 2)));

        //Criar uma resposta simulada do RabbitMQ
        GetResponse response = mock(GetResponse.class);
        when(response.getProps()).thenReturn(mock(com.rabbitmq.client.AMQP.BasicProperties.class));
        when(response.getProps().getHeaders()).thenReturn(headers);

        //Configurar o comportamento do canal para retornar a resposta simulada
        when(channel.basicGet(anyString(), eq(false))).thenReturn(response);

        //Configurar o comportamento do transferService para lançar uma exceção
        doThrow(RuntimeException.class).when(transferService).performTransfer(transferResource);

        //Chamar o método de atualização do status de transferência
        transferMessageConsumer.performTransfer(transferResource, channel, 1L);

        //Verificar se o método performTransfer foi chamado uma vez
        verify(transferService, times(1)).performTransfer(transferResource);

        //Verificar se o método basicNack foi chamado com os parâmetros corretos
        verify(channel, times(1)).basicNack(deliveryTagCaptor.capture(), eq(false), eq(true));

        //Verificar o valor do delivery tag capturado pelo basicNack
        assertEquals(1L, deliveryTagCaptor.getValue());
    }

    @Test
    @DisplayName("Teste de tranferêcia com máximo de retentativas")
    public void testUpdateTransferStatus_MaxRetriesExceeded() throws IOException {
        //Criar um objeto de transferência fictício
        TransferResource transferResource = new TransferResource();

        //Criar um mapa de cabeçalhos simulando a contagem de re-tentativas
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-death", List.of(Map.of("count", 3)));

        //Criar uma resposta simulada do RabbitMQ
        GetResponse response = mock(GetResponse.class);
        when(response.getProps()).thenReturn(mock(com.rabbitmq.client.AMQP.BasicProperties.class));
        when(response.getProps().getHeaders()).thenReturn(headers);

        //Configurar o comportamento do canal para retornar a resposta simulada
        when(channel.basicGet(anyString(), eq(false))).thenReturn(response);

        //Configurar o comportamento do serviço de transferência para lançar uma exceção
        //Após o número máximo de tentativas ser excedido
        doThrow(new RuntimeException("Erro ao realizar a transferência"))
                .when(transferService).performTransfer(transferResource);

        //Chamar o método de atualização do status de transferência
        transferMessageConsumer.performTransfer(transferResource, channel, 1L);

        //Verificar se o método performTransfer foi chamado uma vez
        verify(transferService, times(1)).performTransfer(transferResource);

        //Verificar se o método basicAck foi chamado com os parâmetros corretos
        verify(channel, times(1)).basicAck(anyLong(), eq(false));
    }

}
