/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.services.httpclients;

import com.google.gson.Gson;
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
import io.project.app.broker.dto.PersonDTO;
import java.net.UnknownHostException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

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

    @Retryable(value = {Exception.class}, maxAttempts = 10, backoff = @Backoff(delay = 3000))
    public void sendUser(String request) throws Exception {
        log.info("Start to send !");
        Gson gson = new Gson();
        PersonDTO person = gson.fromJson(request, PersonDTO.class);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("client", "broker");
        String baseUrl = "http://friend:9090/api/v2/people/person/add";
        ///final String baseUrl = this.getFriendUrl("/api/v2/people/person/add");
        final String url = UriComponentsBuilder.fromUriString(baseUrl)
                .toUriString();

//        final Try<ResponseEntity<String>> response = Try.of(()
//                -> this.restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(person, headers), String.class));
        log.info("Try to send !");
        ResponseEntity<String> exchange = this.restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(person, headers), String.class);

        log.error("Status " + exchange.getStatusCode().toString());
        /// log.info("Registered user sent to friend db");
        ///return true;
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
