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

    @Column(name = "wallet_origin")
    private Long walletOrigin;

    @Column(name = "wallet_destiny")
    private Long walletDestiny;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @Column(name = "status_description")
    private String statusDescription;

    @Column(name = "external_account")
    private boolean externalAccount;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
}
