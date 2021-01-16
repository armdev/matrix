package io.project.app.resources;

import io.project.app.services.MessageSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 *
 * @author root
 */
@RestController
@RequestMapping("/api/v2/messages")
@Slf4j
public class MessageResource {
// access via gateway
    // http://localhost:2022/producer/api/v2/messages/echo/hello

    @Autowired
    private MessageSenderService internalRequester;

    @GetMapping(value = "/echo/{message}")
    public Flux<String> echo(@PathVariable("message") String message) {
        log.info("Message for send " + message);
        return internalRequester.send(message);
    
        
    }

}
