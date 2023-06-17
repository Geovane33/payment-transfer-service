package com.purebank.paymenttransferservice.transfer.api.resource;

import com.purebank.paymenttransferservice.transfer.utils.TransferStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferResource {
    @Schema(description = "ID da carteira digital de origem")
    private Long walletOrigin;

    @Schema(description = "ID da carteira digital de destino")
    private Long walletDestiny;

    @Schema(description = "Valor da transferência")
    private BigDecimal amount;

    @Schema(description = "Status da transferência")
    private TransferStatus status;
    @Schema(description = "Conta externa (true se for uma conta externa, false se for interna)")
    private Boolean externalAccount;

}
