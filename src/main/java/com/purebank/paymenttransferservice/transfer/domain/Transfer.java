package com.purebank.paymenttransferservice.transfer.domain;

import com.purebank.paymenttransferservice.common.enums.ProcessStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@Data
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long walletOrigin;

    private Long walletDestiny;

    private BigDecimal amount;

    private ProcessStatus status;

    private String statusDescription;

    private boolean externalAccount;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
}
