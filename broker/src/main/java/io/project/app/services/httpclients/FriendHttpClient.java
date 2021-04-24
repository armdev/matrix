package io.project.app.services.httpclients;

import com.google.gson.Gson;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import io.project.app.broker.dto.PersonDTO;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Service
@Slf4j
public class FriendHttpClient {

    @Autowired
    private EurekaClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(value = {Exception.class}, maxAttempts = 10, backoff = @Backoff(delay = 3000))
    public void sendUser(String request) throws Exception {
        log.info("Start to send !");
        Gson gson = new Gson();
        PersonDTO person = gson.fromJson(request, PersonDTO.class);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("client", "broker");
        headers.add("action", "addaccount");
        ///String baseUrl = "http://friend:9090/api/v2/people/person/add";
        final String baseUrl = this.getFriendUrl("/api/v2/people/person/add");
        final String url = UriComponentsBuilder.fromUriString(baseUrl)
                .toUriString();

//        final Try<ResponseEntity<String>> response = Try.of(()
//                -> this.restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(person, headers), String.class));
        log.info("Try to send, if error !");
        ResponseEntity<String> exchange = this.restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(person, headers), String.class);

        log.info("Status " + exchange.getStatusCode().toString());
        /// log.info("Registered user sent to friend db");
        ///return true;
    }

    @Retryable(value = {Exception.class}, maxAttempts = 10, backoff = @Backoff(delay = 3000))
    public void updateAvatar(String request) throws Exception {
        log.info("Start to avatar update !");
        Gson gson = new Gson();
        PersonDTO person = gson.fromJson(request, PersonDTO.class);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("client", "broker");
        headers.add("action", "avatarupdate");

        final String baseUrl = this.getFriendUrl("/api/v2/people/person/update/avatar");
        final String url = UriComponentsBuilder.fromUriString(baseUrl)
                .toUriString();

        log.info("Try to send, if error !");
        ResponseEntity<String> exchange = this.restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(person, headers), String.class);

        log.info("Status " + exchange.getStatusCode().toString());

    }

    private String getFriendUrl(final String api) {

        final String defaultUrl = "http://friend:9090/" + api;

        try {
            final InstanceInfo instance = this.discoveryClient.getNextServerFromEureka("friend", false);
            if (instance == null || instance.getHomePageUrl() == null || instance.getPort() == 8080) {
                return defaultUrl;
            }
            return String.format("%s%s", instance.getHomePageUrl(), api);
        } catch (Exception e) {
            log.error("Error from  MS:!! " + e.getLocalizedMessage());
            log.error("Exception hapened, service unreachable");
        }
        return defaultUrl;
    }

}
