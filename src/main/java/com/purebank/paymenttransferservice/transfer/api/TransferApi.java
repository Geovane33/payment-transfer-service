package com.purebank.paymenttransferservice.transfer.api;

import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
public interface TransferApi {
    @PostMapping
    ResponseEntity<String> transfer(@Valid @RequestBody TransferResource TransferResource);
}
