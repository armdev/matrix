package io.project.app.signer;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.project.app.api.requests.Claim;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author armena
 */
@Service
@Component
@Slf4j
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient discoveryClient;

    public Try<Claim> verifyAuthToken(String token) {
        String homePage = this.serviceUrl("account");
        Try<Claim> col = Try.of(() -> restTemplate.getForObject(homePage + "api/v2/tokens/verify/{token}", Claim.class, token));
        if (!col.isSuccess()) {
            log.error("Failed to verify account");
            return Try.failure(col.getCause());
        }
        if (col.isSuccess()) {
            log.info("Token check is success");
        }
        return col;
    }

    private String serviceUrl(String service) {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(service, false);

        if (instance != null && instance.getPort() == 8080) {
            return "http://account:9091/";
        }

        if (instance == null || instance.getHomePageUrl() == null) {
            log.error("eureka does not respond");
            return "http://account:9091/";
        }

        return instance.getHomePageUrl();
    }

}
