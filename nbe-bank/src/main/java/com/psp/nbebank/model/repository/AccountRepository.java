package com.psp.nbebank.model.repository;

import com.psp.nbebank.model.entity.Account;
import com.psp.nbebank.model.entity.Card;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findForUpdateByAccountNumber(String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Account> findForShareByAccountNumber(String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Account> findForShareByCard(Card card);
}
