package io.project.app.services;

import io.project.app.domain.Account;

import io.project.app.repositories.AccountRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author armen
 */
@Service
@Component
@Slf4j
public class ProfileService {

    @Autowired
    private AccountRepository accountRepository;

    public Account updateAccount(Account account) {
        final Account savedRecord = accountRepository.save(account);
        return savedRecord;
    }

    public Optional<Account> findAccount(String id) {
        return accountRepository.findById(id);
    }
}
