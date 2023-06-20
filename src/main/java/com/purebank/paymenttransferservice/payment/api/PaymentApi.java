package com.purebank.paymenttransferservice.payment.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public interface PaymentApi {
    @GetMapping("/{walletId}/{paymentIdentifier}")
    ResponseEntity<String> pay(@PathVariable Long walletId, @PathVariable String paymentIdentifier);
}
