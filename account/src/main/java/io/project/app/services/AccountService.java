/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.services;

import io.project.app.domain.Account;
import io.project.app.dto.LoginRequest;
import io.project.app.repositories.AccountRepository;
import io.project.app.utils.PasswordHashUtil;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> registerAccount(Account account) {
        Optional<Account> existingAccount = accountRepository.findByEmail(account.getEmail());
        if (!existingAccount.isPresent()) {
            account.setPassword(PasswordHashUtil.hashPassword(account.getPassword(), account.getPassword()));
            Account save = accountRepository.save(account);
            return Optional.ofNullable(save);
        }
        return Optional.empty();
    }

    public Optional<Account> doLogin(LoginRequest loginRequest) {

        Optional<Account> account = accountRepository.findByEmailAndPassword(loginRequest.getEmail(), PasswordHashUtil.hashPassword(loginRequest.getPassword(),
                loginRequest.getPassword()));
        if (account.isPresent()) {
            return account;
        }

        return Optional.empty();

    }

}
