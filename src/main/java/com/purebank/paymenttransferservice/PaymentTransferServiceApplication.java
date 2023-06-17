package com.purebank.paymenttransferservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PaymentTransferServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentTransferServiceApplication.class, args);
	}

}
