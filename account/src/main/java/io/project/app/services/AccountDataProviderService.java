/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.services;

import io.project.app.domain.Account;
import io.project.app.repositories.AccountRepository;
import java.util.List;
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
public class AccountDataProviderService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> findByEmail(String email) {
        Optional<Account> existingAccount = accountRepository.findByEmail(email);

        return existingAccount;
    }

    public Optional<Account> findById(String id) {
        Optional<Account> existingAccount = accountRepository.findById(id);

        return existingAccount;
    }
    
      public List<Account> getAllUsers() {
        List<Account> existingAccount = accountRepository.findAll();

        return existingAccount;
    }

}
