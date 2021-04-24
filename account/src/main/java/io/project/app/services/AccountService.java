/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.services;

import io.project.app.account.httpclients.BrokerClient;
import io.project.app.domain.Account;
import io.project.app.api.requests.LoginRequest;
import io.project.app.repositories.AccountRepository;
import io.project.app.security.signer.AuthTokenService;

import io.project.app.utils.PasswordHashUtil;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.project.app.api.requests.Device;
import io.project.app.api.responses.ApiAccountResponse;
import io.project.app.api.requests.RegisterRequest;
import io.project.app.account.dto.PersonDTO;

/**
 *
 * @author root
 */
@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private BrokerClient brokerClient;

    public Optional<Account> registerAccount(RegisterRequest registerRequest) throws Exception {
        Optional<Account> existingAccount = accountRepository.findByEmail(registerRequest.getEmail());
        if (!existingAccount.isPresent()) {
            registerRequest.setPassword(PasswordHashUtil.hashPassword(registerRequest.getPassword(), registerRequest.getPassword()));
            Account newAccount = new Account();
            newAccount.setEmail(registerRequest.getEmail());
            newAccount.setPassword(registerRequest.getPassword());
            newAccount.setName(registerRequest.getName());
            Account save = accountRepository.save(newAccount);
            PersonDTO personDTO = new PersonDTO(save.getName(), save.getEmail(), save.getId());
            brokerClient.sendUser(personDTO);
            return Optional.ofNullable(save);
        }
        return Optional.empty();
    }

    public Optional<ApiAccountResponse> doLogin(LoginRequest loginRequest) {
        Optional<Account> account = accountRepository.findByEmailAndPassword(loginRequest.getEmail(), PasswordHashUtil.hashPassword(loginRequest.getPassword(),
                loginRequest.getPassword()));

        if (account.isPresent()) {
            String generateToken = authTokenService.generateToken(account.get(), new Device(true, false, false));
            ApiAccountResponse apiAccountResponse = new ApiAccountResponse();
            apiAccountResponse.setToken(generateToken);
            apiAccountResponse.setAccount(account.get());
            return Optional.of(apiAccountResponse);
        }

        return Optional.empty();

    }

}
