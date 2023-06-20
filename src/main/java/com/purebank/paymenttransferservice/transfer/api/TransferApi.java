package com.purebank.paymenttransferservice.transfer.api;

import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
@Tag(name = "Transfer API", description = "API para realizar transferências de recursos")
public interface TransferApi {

    @Operation(summary = "Realizar transferência", description = "Realiza uma transferência de recursos entre contas")
    @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    @PostMapping
    ResponseEntity<String> transfer(
            @Parameter(description = "Informações da transferência", required = true,
                    content = @Content(schema = @Schema(implementation = TransferResource.class)))
            @Valid @RequestBody TransferResource transferResource);
}
