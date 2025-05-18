package com.psp.instapay.model.repository;

import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Bank;
import com.psp.instapay.model.entity.User;
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
    Optional<Account> findByUserAndAccountNumber(User user, String accountNumber);

    List<Account> findAllByUser(User user);

    List<Account> findAllByUserAndBank(User user, Bank bank);

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByUserAndAccountNumber(User user, String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findForUpdateByAccountNumber(String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findForUpdateByUserAndAccountNumber(User user, String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    void deleteByAccountNumber(String accountNumber);
}