package io.project.app.services.rsockets;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import io.project.app.services.httpclients.FriendHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@Service
@Slf4j
public class RouteController {

    @Autowired
    private FriendHttpClient friendHttpClient;

    @MessageMapping("msend")
    public Flux<String> msend(String message) throws Exception {
        log.info("msend channel publish, sending to frined microservice:::: " + message);
        friendHttpClient.sendUser(message);
        return Flux.just(message);
    }

}
