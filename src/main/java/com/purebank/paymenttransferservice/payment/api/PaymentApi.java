package com.purebank.paymenttransferservice.payment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment API", description = "API para realizar pagamentos")
public interface PaymentApi {

    @Operation(summary = "Realizar pagamento", description = "Realiza um pagamento com base no ID da carteira e no identificador do pagamento")
    @ApiResponse(responseCode = "200", description = "Pagamento realizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    @GetMapping("/{walletId}/{paymentIdentifier}")
    ResponseEntity<String> pay(
            @Parameter(description = "ID da carteira", required = true)
            @PathVariable Long walletId,
            @Parameter(description = "Identificador do pagamento", required = true)
            @PathVariable String paymentIdentifier);
}
