package com.purebank.paymenttransferservice.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purebank.paymenttransferservice.payment.service.PaymentService;
import com.purebank.paymenttransferservice.transfer.TransferController;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
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

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService paymentService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Teste de pagamento")
    void payTest() throws Exception {
        Long walletId = 1L;
        String paymentIdentifier = "1231231";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/payment/{walletId}/{paymentIdentifier}", walletId, paymentIdentifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentIdentifier)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
