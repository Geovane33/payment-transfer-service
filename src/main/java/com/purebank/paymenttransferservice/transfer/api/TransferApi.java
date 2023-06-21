package com.purebank.paymenttransferservice.transfer.api;

import com.purebank.paymenttransferservice.exceptions.ApiErrorMessage;
import com.purebank.paymenttransferservice.transfer.resource.TransferResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
@Tag(name = "Transfer API", description = "API para realizar transferências de recursos")
public interface TransferApi {

    @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Carteira não encontrada",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida devido a dados incorretos ou ausentes",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorMessage.class)))
    @Operation(summary = "Realizar transferência",
            description = "Realiza uma transferência de recursos entre contas")
    @PostMapping(consumes =  MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> transfer(
            @Parameter(description = "Informações da transferência", required = true,
                    content = @Content(schema = @Schema(implementation = TransferResource.class)))
            @Valid @RequestBody TransferResource transferResource);
}
