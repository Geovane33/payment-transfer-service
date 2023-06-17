//package com.purebank.paymenttransferservice.external.wallet.service;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.math.BigDecimal;
//
//@FeignClient(url = "https://localhost:8080/api/wallet", name = "walletService")
//public interface WalletService {
//
//    @GetMapping("/{walletId}")
//    BigDecimal getBalance(@RequestParam Long walletId);
//}
