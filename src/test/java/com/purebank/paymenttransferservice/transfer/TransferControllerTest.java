package com.purebank.paymenttransferservice.transfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purebank.paymenttransferservice.exceptions.ApiErrorMessage;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@WebMvcTest(TransferController.class)
public class TransferControllerTest {
    public static final String API_TRANSFER = "/api/transfer";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransferService transferService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Realizar transferência")
    public void transferTest() throws Exception {
        TransferResource transferResource = new TransferResource();
        transferResource.setWalletOrigin(1L);
        transferResource.setWalletDestiny(2L);
        transferResource.setAmount(BigDecimal.valueOf(100));
        transferResource.setExternalAccount(false);
        mockMvc.perform(MockMvcRequestBuilders.post(API_TRANSFER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferResource)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Realizar transferência com valor máximo")
    public void transferMaxValueTest() throws Exception {
        TransferResource transferResource = new TransferResource();
        transferResource.setWalletOrigin(1L);
        transferResource.setWalletDestiny(2L);
        transferResource.setAmount(new BigDecimal("1000000.00"));
        transferResource.setExternalAccount(false);
        mockMvc.perform(MockMvcRequestBuilders.post(API_TRANSFER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferResource)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Realizar transferência com valor igual a 0")
    public void transferZeroValueTest() throws Exception {
        TransferResource transferResource = new TransferResource();
        transferResource.setWalletOrigin(1L);
        transferResource.setWalletDestiny(2L);
        transferResource.setAmount(new BigDecimal("0"));
        transferResource.setExternalAccount(false);
        mockMvc.perform(MockMvcRequestBuilders.post(API_TRANSFER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferResource)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Teste de validação das propriedades da transferência")
    public void validateTransferPropertiesTest() throws Exception {
        TransferResource transferResource = new TransferResource();
        transferResource.setWalletOrigin(null);
        transferResource.setWalletDestiny(null);
        transferResource.setAmount(null);
        transferResource.setExternalAccount(null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(API_TRANSFER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferResource)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(responseBody.contains("Informe a carteira de origem"));
        Assertions.assertTrue(responseBody.contains("Informe a carteira de destino"));
        Assertions.assertTrue(responseBody.contains("Informe o valor da transferencia"));
        Assertions.assertTrue(responseBody.contains("Informe se a conta de destino interna ou externa"));
    }
}
