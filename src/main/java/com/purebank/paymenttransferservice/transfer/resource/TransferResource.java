package com.purebank.paymenttransferservice.transfer.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purebank.paymenttransferservice.common.enums.ProcessStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferResource {
    @Schema(description = "ID da carteira digital de origem")
    @NotBlank(message = "Informe a carteira de origem")
    private Long walletOrigin;

    @Schema(description = "ID da carteira digital de destino")
    @NotBlank(message = "Informe a carteira de destino")
    private Long walletDestiny;

    @Schema(description = "Valor da transferência")
    @NotNull(message = "Informe o valor da transferencia")
    private BigDecimal amount;

    @Schema(description = "Descrição do status")
    private String statusDescription;

    @Schema(description = "Status da transferência")
    private ProcessStatus status;
    @Schema(description = "Conta externa (true se for uma conta externa, false se for interna)")
    private Boolean externalAccount;

    @JsonIgnore
    private String uuidActivity;
}
