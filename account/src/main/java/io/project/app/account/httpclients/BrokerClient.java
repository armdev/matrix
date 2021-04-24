/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.account.httpclients;

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
import io.project.app.account.dto.PersonDTO;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author root
 */
@Service
@Slf4j
public class BrokerClient {

    @Autowired
    private EurekaClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(value = {Exception.class}, maxAttempts = 20, backoff = @Backoff(delay = 3000))
    @Async
    public void sendUser(PersonDTO request) throws Exception {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("client", "account");
        headers.add("action", "addaccount");
        log.info("Sending registered user to hell");

        final String baseUrl = this.getBrokerURl("/api/v2/friends/send");
        final String url = UriComponentsBuilder.fromUriString(baseUrl)
                .toUriString();

//        final Try<ResponseEntity<PersonDTO>> response = Try.of(()
//                -> this.restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(request, headers), PersonDTO.class));
        log.info("Sending to broker again if fail or error");
        ResponseEntity<PersonDTO> exchange = this.restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(request, headers), PersonDTO.class);

        log.error("Status " + exchange.getStatusCode().toString());

    }

    @Retryable(value = {Exception.class}, maxAttempts = 20, backoff = @Backoff(delay = 3000))
    @Async
    public void updateAvatar(PersonDTO request) throws Exception {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("client", "account");
        headers.add("action", "updateavatar");
        log.info("Sending registered user to hell");

        final String baseUrl = this.getBrokerURl("/api/v2/friends/send");
        final String url = UriComponentsBuilder.fromUriString(baseUrl)
                .toUriString();

        log.info("Sending to broker again if fail or error");
        ResponseEntity<PersonDTO> exchange = this.restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(request, headers), PersonDTO.class);

        log.error("Status " + exchange.getStatusCode().toString());

    }

    private String getBrokerURl(final String api) {

        final String defaultUrl = "http://broker:9092/" + api;

        try {
            final InstanceInfo instance = this.discoveryClient.getNextServerFromEureka("broker", false);
            if (instance == null || instance.getHomePageUrl() == null || instance.getPort() == 8080) {
                return defaultUrl;
            }
            return String.format("%s%s", instance.getHomePageUrl(), api);
        } catch (Exception e) {
            log.error("Error from  MS: " + e.getLocalizedMessage());
        }

        return defaultUrl;
    }

}
