package com.psp.nbebank.model.repository;

import com.psp.nbebank.model.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findForUpdateByAccountNumber(String accountNumber);

    @Query(
            "SELECT a.balance FROM Account a WHERE a.accountNumber = :accountNumber"
    )
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Double> findBalanceByAccountNumber(@Param("accountNumber") String accountNumber);

    List<Account> findByCardId(Long cardId);
}
