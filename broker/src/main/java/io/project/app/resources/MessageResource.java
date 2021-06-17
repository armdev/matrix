package io.project.app.resources;

import com.google.gson.Gson;
import io.project.app.services.MessageSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import io.project.app.broker.dto.PersonDTO;
import org.springframework.http.MediaType;

/**
 *
 * @author root
 */
@RestController
@RequestMapping("/api/v2/friends")
@Slf4j
public class MessageResource {

    @Autowired
    private MessageSenderService internalRequester;

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> send(@RequestBody PersonDTO message) {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(message);
        log.info("Received person data  " + jsonInString);
        return internalRequester.send(jsonInString);

    }

}
