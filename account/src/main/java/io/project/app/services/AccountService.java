/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.services;

import io.project.app.domain.Account;
import io.project.app.repositories.AccountRepository;
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

    public Account registerAccount(Account account) {
        Optional<Account> existingAccount = accountRepository.findByEmail(account.getEmail());
        if (!existingAccount.isPresent()) {
            Account save = accountRepository.save(account);
            return save;
        }
        
        return new Account();
    }

}
