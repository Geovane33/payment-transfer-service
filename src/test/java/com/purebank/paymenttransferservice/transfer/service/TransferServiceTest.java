package com.purebank.paymenttransferservice.transfer.service;

import com.purebank.paymenttransferservice.transfer.service.Impl.TransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(properties = {"spring.flyway.enabled=false"})
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc
@SpringBootTest(classes = {TransferService.class, TransferServiceImpl.class})
public class TransferServiceTest {
    @Autowired
    private TransferService transferService;

    @BeforeEach
    void setup() {
        // Configuração inicial, se necessário
    }

    @Test
    void createWalletTest() {}

}
