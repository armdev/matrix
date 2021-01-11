package io.project.app.resources.token;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.project.app.api.responses.ResponseMessage;
import io.project.app.security.signer.AuthTokenService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author root
 */
@RestController
@RequestMapping("/api/v2/tokens")
@Slf4j
public class TokenController {

    @Autowired
    private AuthTokenService authTokenService;

    @GetMapping(path = "/verify/{token}", produces =MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> verifyToken(@PathVariable String token
          
    ) {

        try {
        
            if (token == null) {
                log.error("Did not find token in the request");
                return ResponseEntity.badRequest().body(new ResponseMessage("Please put token in the request"));
            }

            //log.info("Validating Token ");
            boolean validateToken = authTokenService.validateToken(token);

            if (!validateToken) {
                log.error("Token is not valid!");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseMessage("Token is not valid"));
            }

        } catch (RestClientException e) {
            log.error("Exception happened " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Looks like we've lost connection, try the operation again"));
        }
        // log.info("Token accepted");
        return ResponseEntity.accepted().body(authTokenService.getAllClaimsFromToken(token));
    }

}
