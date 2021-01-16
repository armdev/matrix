package io.project.app.services.rsockets;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
@Service
@Slf4j
public class RouteController {

    @MessageMapping("msend")
    public Flux<String> msend(String message) {
        log.info("msend channel publish " + message);
        return Flux.just(message, " "+System.currentTimeMillis());
    }

}
