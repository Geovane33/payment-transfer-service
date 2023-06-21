package com.purebank.paymenttransferservice.transfer.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.purebank.paymenttransferservice.common.enums.ProcessStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferResource {
    @Schema(description = "ID da carteira digital de origem")
    @NotNull(message = "Informe a carteira de origem")
    private Long walletOrigin;

    @Schema(description = "ID da carteira digital de destino")
    @NotNull(message = "Informe a carteira de destino")
    private Long walletDestiny;

    @Schema(description = "Valor da transferência")
    @NotNull(message = "Informe o valor da transferencia")
    private BigDecimal amount;

    @Schema(description = "Conta externa (true se for uma conta externa, false se for interna)")
    @NotNull(message = "Informe se a conta de destino interna ou externa")
    private Boolean externalAccount;

    @Schema(description = "Status da transferência")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProcessStatus status;

    @Schema(description = "Descrição da transferência")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String statusDescription;

    @JsonIgnore
    private String uuidActivity;
}
