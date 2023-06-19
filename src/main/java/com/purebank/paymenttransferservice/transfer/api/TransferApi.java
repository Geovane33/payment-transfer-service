package com.purebank.paymenttransferservice.transfer.api;

import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
public interface TransferApi {
    @PostMapping
    ResponseEntity<String> transfer(@RequestBody TransferResource TransferResource);
}
