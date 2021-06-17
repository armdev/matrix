package io.project.app.resources.account;

import io.project.app.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.project.app.api.requests.LoginRequest;
import io.project.app.api.requests.RegisterRequest;
import java.util.Optional;
import io.project.app.api.responses.ApiAccountResponse;
import io.project.app.domain.Account;
import org.springframework.http.MediaType;

/**
 *
 * @author root
 */
@RestController
@RequestMapping("/api/v2/accounts")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PutMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> login(
            @RequestBody(required = true) LoginRequest loginRequest
    ) {

        Optional<ApiAccountResponse> doLogin = accountService.doLogin(loginRequest);
        if (doLogin.isPresent()) {
            log.info("Login success");
            return ResponseEntity.ok().header("Authorization", doLogin.get().getToken()).body(doLogin.get());
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(("Could not login account"));
    }

    @PutMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> register(
            @RequestBody(required = true) RegisterRequest registerRequest
    ) throws Exception {

        Optional<Account> doRegister = accountService.registerAccount(registerRequest);
        if (doRegister.isPresent()) {
            ApiAccountResponse apiAccountResponse = new ApiAccountResponse();
            apiAccountResponse.setAccount(doRegister.get());
            log.info("Register success");
            return ResponseEntity.status(HttpStatus.OK).body(apiAccountResponse);
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(("Could not register user"));
    }

}
