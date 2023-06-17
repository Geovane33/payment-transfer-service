//package com.purebank.accountservice.transfer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.purebank.accountservice.transfer.service.TransferService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.math.BigDecimal;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(TransferController.class)
//public class TransferControllerTest {
//	@Autowired
//	private MockMvc mvc;
//	private ObjectMapper objectMapper = new ObjectMapper();
//	private String API_WALLET = "/api/wallet";
//	@MockBean
//	private TransferService transferService;
//
//	@BeforeEach
//	void setup() {
//		// Configuração inicial, se necessário
//	}
//
//	@Test
//	void createWalletTest() throws Exception {
//		PaymentResource paymentResource = new PaymentResource();
//		paymentResource.setBalance(BigDecimal.valueOf(10));
//		paymentResource.setName("Test");
//		paymentResource.setId(1L);
//
//		Mockito.doReturn(paymentResource).when(transferService).createWallet(paymentResource);
//
//		String requestBody = "{ \"id\": 1, \"name\": \"Test\", \"balance\": 10 }";
//		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(API_WALLET)
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(requestBody))
//				.andExpect(status().isOk())
//				.andReturn();
//
//		String responseBody = mvcResult.getResponse().getContentAsString();
//		PaymentResource responseWallet = objectMapper.readValue(responseBody, PaymentResource.class);
//
//		Assertions.assertEquals(paymentResource, responseWallet);
//	}
//
//	@Test
//	void getWalletByIdTest() throws Exception {
//		PaymentResource paymentResource = new PaymentResource();
//		paymentResource.setBalance(BigDecimal.valueOf(10));
//		paymentResource.setName("Test");
//		paymentResource.setId(1L);
//
//		Mockito.doReturn(paymentResource).when(transferService).getWalletById(paymentResource.getId());
//		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(API_WALLET + "/" + paymentResource.getId())
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//
//		String responseBody = mvcResult.getResponse().getContentAsString();
//		PaymentResource responseWallet = objectMapper.readValue(responseBody, PaymentResource.class);
//
//		Assertions.assertEquals(paymentResource, responseWallet);
//	}
//
//	@Test
//	void getBalanceTest() throws Exception {
//		PaymentResource paymentResource = new PaymentResource();
//		paymentResource.setBalance(BigDecimal.valueOf(10));
//		paymentResource.setName("Test");
//		paymentResource.setId(1L);
//
//		Mockito.doReturn(paymentResource.getBalance()).when(transferService).getBalance(paymentResource.getId());
//		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(API_WALLET + "/" + paymentResource.getId() + "/balance")
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//
//		String responseBody = mvcResult.getResponse().getContentAsString();
//		BigDecimal responseBalance = objectMapper.readValue(responseBody, BigDecimal.class);
//
//		Assertions.assertEquals(responseBalance, paymentResource.getBalance());
//	}
//
//	@Test
//	void updateWalletTest() throws Exception {
//		PaymentResource paymentResource = new PaymentResource();
//		paymentResource.setBalance(BigDecimal.valueOf(10));
//		paymentResource.setName("Test");
//		paymentResource.setId(1L);
//
//		String requestBody = "{ \"id\": 1, \"name\": \"Test\", \"balance\": 10 }";
//		PaymentResource paymentResourceUpdate = objectMapper.readValue(requestBody, PaymentResource.class);
//		paymentResourceUpdate.setName("Test update");
//
//		Mockito.doReturn(paymentResourceUpdate).when(transferService).updateWallet(paymentResource);
//		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(API_WALLET)
//						.contentType(MediaType.APPLICATION_JSON)
//				.content(requestBody))
//				.andExpect(status().isOk())
//				.andReturn();
//
//		String responseBody = mvcResult.getResponse().getContentAsString();
//		PaymentResource responseWallet = objectMapper.readValue(responseBody, PaymentResource.class);
//
//		Assertions.assertEquals(paymentResourceUpdate, responseWallet);
//	}
//}
