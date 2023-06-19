package com.purebank.paymenttransferservice.payment.api;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public interface PaymentApi {
    @PostMapping("/{walletId}")
    ResponseEntity<String> pay(@PathVariable Long walletId, @NotNull @RequestBody Long paymentIdentifier);
}
