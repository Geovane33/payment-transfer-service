package com.purebank.paymenttransferservice.transfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purebank.paymenttransferservice.payment.PaymentController;
import com.purebank.paymenttransferservice.transfer.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PaymentController.class)
public class TransferControllerTest {
	@Autowired
	private MockMvc mvc;
	private ObjectMapper objectMapper = new ObjectMapper();
	private String API_WALLET = "/api/wallet";
	@MockBean
	private TransferService transferService;

	@BeforeEach
	void setup() {
		// Configuração inicial, se necessário
	}

	@Test
	void createWalletTest() throws Exception {

	}
}
