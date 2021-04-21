/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.account.httpclients;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.vavr.control.Try;
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

    public boolean sendUser(PersonDTO request) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("client", "account");
        log.info("Sending registered user to hell");

        final String baseUrl = this.getBrokerURl("/api/v2/friends/send");
        final String url = UriComponentsBuilder.fromUriString(baseUrl)
                .toUriString();

        final Try<ResponseEntity<PersonDTO>> response = Try.of(()
                -> this.restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(request, headers), PersonDTO.class));

        if (response.isFailure()) {
            log.error("Send data to broker failed: " + response.getCause().getLocalizedMessage());
            return false;
        }

        log.info("Registered user sent to neo db");
        return true;
    }

    private String getBrokerURl(final String api) {

        final String defaultUrl = "http://producer:9092/" + api;

        try {
            final InstanceInfo instance = this.discoveryClient.getNextServerFromEureka("producer", false);
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
