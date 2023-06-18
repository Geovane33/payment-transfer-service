package com.purebank.paymenttransferservice.transfer.repository;

import com.purebank.paymenttransferservice.transfer.domain.Transfer;
import com.purebank.paymenttransferservice.transfer.utils.TransferStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@Transactional
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Transfer findTransferById(@Param("id") Long walletId);

    @Modifying
    @Query("UPDATE Transfer t SET t.status =:status WHERE t.id =:id")
    void updateStatus(@Param("id") Long id, @Param("status") TransferStatus status);

//    @Query("SELECT w.balance FROM Wallet w WHERE w.id = :id")
//    BigDecimal getBalanceByWalletId(@Param("id") Long walletId);
}
