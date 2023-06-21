package com.purebank.paymenttransferservice.external.wallet.service;

import com.purebank.paymenttransferservice.external.wallet.resourcer.WalletResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "walletService", url = "http://wallet-service:8080")
public interface WalletServiceFeignClient {

    @GetMapping("/api/wallet/{walletId}")
    WalletResource getWallet(@PathVariable Long walletId);

    @PutMapping("/api/wallet")
    WalletResource updateWallet(@RequestBody WalletResource walletResource);
}
