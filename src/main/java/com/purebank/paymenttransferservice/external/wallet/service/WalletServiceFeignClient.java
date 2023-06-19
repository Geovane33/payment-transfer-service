package com.purebank.paymenttransferservice.external.wallet.service;

import com.purebank.paymenttransferservice.external.wallet.resourcer.WalletResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "http://localhost:8080/api/wallet", name = "walletService")
public interface WalletServiceFeignClient {

    @GetMapping("/{walletId}")
    WalletResource getWallet(@PathVariable Long walletId);

    @PutMapping()
    WalletResource updateWallet(@RequestBody WalletResource walletResource);
}
