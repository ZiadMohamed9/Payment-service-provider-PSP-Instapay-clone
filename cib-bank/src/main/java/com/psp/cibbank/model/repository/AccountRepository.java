package com.psp.cibbank.model.repository;

import com.psp.cibbank.model.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT a.balance FROM Account a WHERE a.accountNumber = :accountNumber")
    Double findBalanceByAccountNumber(@Param("accountNumber") String accountNumber);

    boolean existsByAccountNumber(String accountNumber);
}
