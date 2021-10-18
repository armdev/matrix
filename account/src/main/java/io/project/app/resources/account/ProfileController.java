package io.project.app.resources.account;

import io.micrometer.core.annotation.Timed;
import io.project.app.api.requests.PasswordUpdateRequest;
import io.project.app.domain.Account;
import io.project.app.services.ProfileService;
import io.project.app.utils.PasswordHashUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/profiles")
@Slf4j
public class ProfileController {

    @Autowired
    private ProfileService profileService;


    @PutMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> put(@RequestBody Account account, @RequestHeader(name = "Authorization", required = true) String token) {

        log.info(token);
        log.info("Started user update");
        if (account.getId() != null) {
            Optional<Account> findAccount = profileService.findAccount(account.getId());
            if (findAccount.isPresent()) {
                log.info("Accpiunt update with avatar id " +account.getAvatarId());
                final Account savedRecord = profileService.updateAccount(account);
                return ResponseEntity.status(HttpStatus.OK).body(savedRecord);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Did not find account for update");
    }

    @PutMapping(path = "/account/password", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> update(@RequestBody PasswordUpdateRequest passwordUpdate, @RequestHeader(name = "Authorization", required = true) String token) {
        log.info("Started password update");
        if (passwordUpdate.getId() != null) {
            Optional<Account> findAccount = profileService.findAccount(passwordUpdate.getId());
            if (findAccount.isPresent()) {
                findAccount.get().setPassword(PasswordHashUtil.hashPassword(passwordUpdate.getPassword(),findAccount.get().getId()));
                final Account savedRecord = profileService.updateAccount(findAccount.get());
                return ResponseEntity.status(HttpStatus.OK).body(savedRecord);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Did not find account for update");
    }

}
