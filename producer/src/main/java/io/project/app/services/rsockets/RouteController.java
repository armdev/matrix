package io.project.app.services.rsockets;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import io.project.app.broker.dto.PersonDTO;
import io.project.app.services.httpclients.BrokerClient;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@Service
@Slf4j
public class RouteController {
    
    @Autowired    
    private BrokerClient brokerClient;

    @MessageMapping("msend")
    public Flux<String> msend(String message) {
        log.info("msend channel publish, sending to frined microservice:::: " + message);
        brokerClient.sendUser(message);
        return Flux.just(message);
    }

}
