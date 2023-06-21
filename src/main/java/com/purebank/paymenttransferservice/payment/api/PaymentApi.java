package com.purebank.paymenttransferservice.payment.api;

import com.purebank.paymenttransferservice.exceptions.ApiErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment API", description = "API para realizar pagamentos")
public interface PaymentApi {

    @ApiResponse(responseCode = "200", description = "Pagamento realizado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Carteira não encontrada ou pagamento não encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida devido a dados incorretos ou ausentes",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorMessage.class)))
    @Operation(summary = "Realizar pagamento",
            description = "Realiza um pagamento com base no ID da carteira e no identificador do pagamento")
    @PostMapping(consumes =  MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/{walletId}/{paymentIdentifier}")
    ResponseEntity<Void> pay(@Parameter(description = "ID da carteira", required = true)
            @PathVariable Long walletId,
            @Parameter(description = "Identificador do pagamento", required = true)
            @PathVariable String paymentIdentifier);
}
