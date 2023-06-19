package com.purebank.paymenttransferservice.transfer.resource;

import com.purebank.paymenttransferservice.transfer.enums.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletActivityResource implements Serializable {
    private String uuidActivity;

    private Long walletId;

    private String activityType;

    private ProcessStatus status;

    private BigDecimal amount;

    private String description;

    private LocalDateTime activityDate;

    private LocalDateTime creationDate;

}
