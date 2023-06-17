package com.purebank.paymenttransferservice.transfer.repository;

import com.purebank.paymenttransferservice.transfer.domain.Transfer;
import com.purebank.paymenttransferservice.transfer.utils.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Transfer findTransferById(@Param("id") Long walletId);


    Transfer updateStatus(@Param("id") Long id, TransferStatus status);

//    @Query("SELECT w.balance FROM Wallet w WHERE w.id = :id")
//    BigDecimal getBalanceByWalletId(@Param("id") Long walletId);
}
