package io.project.app.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageSenderService {

    private final RSocketRequester externalRequester;

    public MessageSenderService(RSocketRequester.Builder builder) {
        this.externalRequester = builder
                .dataMimeType(MediaType.ALL)
                .tcp("localhost", 2050);
    }

    public Flux<String> send(String message) {
        log.info("Send to channel " + message);

        return externalRequester.route("msend").data(message).retrieveFlux(String.class);

    }


}
