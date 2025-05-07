package com.psp.instapay.model.service.impl;

import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Bank;
import com.psp.instapay.model.entity.User;
import com.psp.instapay.model.repository.AccountRepository;
import com.psp.instapay.model.repository.BankRepository;
import com.psp.instapay.model.repository.UserRepository;
import com.psp.instapay.model.service.AccountService;
import com.psp.instapay.common.client.BankClientFactory;
import com.psp.instapay.common.exception.AccountAlreadyExistsException;
import com.psp.instapay.common.exception.AccountNotFoundException;
import com.psp.instapay.common.exception.BankNotFoundException;
import com.psp.instapay.common.exception.UserNotFoundException;
import com.psp.instapay.common.mapper.AccountMapper;
import com.psp.instapay.model.dto.request.AddAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final AccountMapper accountMapper;
    private final BankClientFactory bankClientFactory;

    @Override
    public AccountDTO getAccountById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return accountRepository.findByUserAndId(user, id)
                .map(accountMapper::toAccountDTO)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    public AccountDTO getAccountByAccountNumber(String accountNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return accountRepository.findByUserAndAccountNumber(user, accountNumber)
                .map(accountMapper::toAccountDTO)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return accountRepository.findAllByUser(user)
                .stream()
                .map(accountMapper::toAccountDTO)
                .toList();
    }

    @Override
    public List<AccountDTO> getAllAccountsByBankName(String bankName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Bank bank = bankRepository.findByName(bankName.toUpperCase())
                .orElseThrow(() -> new BankNotFoundException("Bank not found"));

        return accountRepository.findAllByUserAndBank(user, bank)
                .stream()
                .map(accountMapper::toAccountDTO)
                .toList();
    }

    @Override
    public AccountDTO addAccount(AddAccountRequest addAccountRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String bankName = addAccountRequest.getBankName().toUpperCase();
        Bank bank = bankRepository.findByName(bankName)
                .orElseThrow(() -> new BankNotFoundException("Bank not found"));

        if (accountRepository.existsByUserAndAccountNumber(user, addAccountRequest.getAccountNumber()))
            throw new AccountAlreadyExistsException("Account already exists");

        Double balance = (Double) bankClientFactory.getBankClient(bankName)
                .getBalance(addAccountRequest.getAccountNumber()).getData();

        if (balance == null)
            throw new AccountNotFoundException("Account not found");

        Account account = accountMapper.toAccount(addAccountRequest);
        account.setUser(user);
        account.setBank(bank);
        account.setBalance(balance);

        return accountMapper.toAccountDTO(accountRepository.save(account));
    }

    @Override
    public void deleteAccount(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (accountRepository.existsByUserAndId(user, id)) {
            accountRepository.deleteById(id);
        } else {
            throw new AccountNotFoundException("Account not found");
        }
    }
}
