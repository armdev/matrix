package io.project.app.resources.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import io.project.app.api.responses.ApiAccountResponse;
import io.project.app.domain.Account;
import io.project.app.services.AccountDataProviderService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author root
 */
@RestController
@RequestMapping("/api/v2/validations")
@Slf4j
public class AccountDataProvider {

    @Autowired
    private AccountDataProviderService accountDataProviderService;

    @GetMapping(path = "/find/email", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> get(
            @RequestParam(required = true) String email
    ) {

        Optional<Account> doRegister = accountDataProviderService.findByEmail(email);
        if (doRegister.isPresent()) {
            ApiAccountResponse apiAccountResponse = new ApiAccountResponse();
            apiAccountResponse.setAccount(doRegister.get());
            log.info("get success");
            return ResponseEntity.status(HttpStatus.OK).body(apiAccountResponse);
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(("Could not find user"));
    }

    @GetMapping(path = "/find/by/id", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> fetch(
            @RequestParam(required = true) String id
    ) {

        Optional<Account> doRegister = accountDataProviderService.findById(id);
        if (doRegister.isPresent()) {
            ApiAccountResponse apiAccountResponse = new ApiAccountResponse();
            apiAccountResponse.setAccount(doRegister.get());
            log.info("fetch success");
            return ResponseEntity.status(HttpStatus.OK).body(apiAccountResponse);
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(("Could not find user"));
    }

    @GetMapping(path = "/find/all/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> findAll() {

        List<Account> findAll = accountDataProviderService.getAllUsers();
        if (!findAll.isEmpty()) {
            ApiAccountResponse apiAccountResponse = new ApiAccountResponse();
            apiAccountResponse.getAccountList().addAll(findAll);
            log.info("Get all users done success");
            return ResponseEntity.status(HttpStatus.OK).body(apiAccountResponse);
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(("Could not find account list"));
    }

}
