package com.purebank.paymenttransferservice.transfer.api;

import com.purebank.paymenttransferservice.transfer.api.resource.TransferResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
public interface TransferApi {
    @PostMapping
    ResponseEntity<String> transfer(@RequestBody TransferResource TransferResource);


    @GetMapping("/{transferId}")
    ResponseEntity<TransferResource> getTransferById(@PathVariable Long transferId);

    @GetMapping
    ResponseEntity<Void> teste(@RequestParam String msg);
}
