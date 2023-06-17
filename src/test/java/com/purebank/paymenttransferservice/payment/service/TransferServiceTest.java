package com.purebank.paymenttransferservice.payment.service;//package com.purebank.accountservice.transfer.service;
//
//import com.purebank.accountservice.payment.repository.PaymentRepository;
//import com.purebank.accountservice.transfer.domain.Transfer;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.math.BigDecimal;
//
////@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
////@TestPropertySource(properties = {"spring.flyway.enabled=false"})
////@ExtendWith(SpringExtension.class)
////@ExtendWith(MockitoExtension.class)
////@AutoConfigureMockMvc
//@SpringBootTest(classes = TransferService.class)
//public class TransferServiceTest {
//    @Autowired
//    private TransferService transferService;
//
//    @MockBean
//    private PaymentRepository paymentRepository;
//
//    @BeforeEach
//    void setup() {
//        // Configuração inicial, se necessário
//    }
//
//    @Test
//    void createWalletTest() {
//        Transfer walletEntity = new Transfer();
//        walletEntity.setBalance(BigDecimal.valueOf(10));
//        walletEntity.setName("Test");
//        walletEntity.setId(1L);
//        Mockito.doReturn(walletEntity).when(paymentRepository).save(Mockito.any());
//
//        PaymentResource paymentResource = new PaymentResource();
//        paymentResource.setBalance(BigDecimal.valueOf(10));
//        paymentResource.setName("Test");
//        PaymentResource walletById = transferService.createWallet(paymentResource);
//
//        paymentResource.setId(1L);
//        Assertions.assertEquals(paymentResource, walletById);
//    }
//
//    @Test
//    void getWalletByIdTest() {
//        Transfer walletEntity = new Transfer();
//        walletEntity.setBalance(BigDecimal.valueOf(10));
//        walletEntity.setName("Test");
//        walletEntity.setId(1L);
//        Mockito.doReturn(walletEntity).when(paymentRepository).findWalletById(walletEntity.getId());
//
//        PaymentResource walletById = transferService.getWalletById(walletEntity.getId());
//
//        PaymentResource paymentResource = new PaymentResource();
//        paymentResource.setBalance(BigDecimal.valueOf(10));
//        paymentResource.setName("Test");
//        paymentResource.setId(1L);
//        Assertions.assertEquals(paymentResource, walletById);
//    }
//
//    @Test
//    void getBalanceTest() {
//        Transfer walletEntity = new Transfer();
//        walletEntity.setBalance(BigDecimal.valueOf(10));
//        walletEntity.setName("Test");
//        walletEntity.setId(1L);
//        Mockito.doReturn(walletEntity.getBalance()).when(paymentRepository).getBalanceByWalletId(walletEntity.getId());
//
//        BigDecimal balance = transferService.getBalance(walletEntity.getId());
//
//        Assertions.assertEquals(walletEntity.getBalance(), balance);
//    }
//
//    @Test
//    void updateWalletTest() {
//        Payment walletEntity = new Payment();
//        walletEntity.setBalance(BigDecimal.valueOf(10));
//        walletEntity.setName("Test");
//        walletEntity.setId(1L);
//        Mockito.doReturn(walletEntity).when(paymentRepository).save(walletEntity);
//
//        PaymentResource paymentResource = new PaymentResource();
//        paymentResource.setBalance(BigDecimal.valueOf(10));
//        paymentResource.setName("Test");
//        paymentResource.setId(1L);
//
//        PaymentResource WalletUpdated = transferService.updateWallet(paymentResource);
//
//        Assertions.assertEquals(paymentResource, WalletUpdated);
//    }
//}
