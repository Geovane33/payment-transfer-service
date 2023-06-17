package com.purebank.paymenttransferservice.transfer.domain;

import com.purebank.paymenttransferservice.transfer.utils.TransferStatus;
import com.purebank.paymenttransferservice.transfer.utils.TransferType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@Data
public class ActivityHistory {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Long walletId;

        private TransferType type;

        private BigDecimal amount;

        private LocalDateTime timestamp;

        private TransferStatus status;

        @Column(name = "creation_date")
        private LocalDateTime creationDate;
        @Column(name = "last_update")
        private LocalDateTime lastUpdate;
}
