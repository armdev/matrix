package io.project.neo4j.resources;

import io.micrometer.core.annotation.Timed;
import io.project.neo4j.domain.Person;
import io.project.neo4j.dto.PersonDTO;
import io.project.neo4j.dto.PersonResponse;
import io.project.neo4j.services.FriendService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/people")
@Slf4j
public class PersonResource {

    @Autowired
    private FriendService friendService;

    @GetMapping(path = "/person/one", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> get(@RequestParam(required = true) String personId,
            @RequestHeader(name = "Authorization") String token
    ) {
        log.info("API: person/one");
        Optional<Person> account = friendService.findPerson(personId);
        if (account.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(account.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Did not find account");
    }

    @GetMapping(path = "/person/all/data", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> fetch(@RequestHeader(name = "Authorization") String token) {
        log.info("find all");
        List<Person> accountList = friendService.findAll();
        PersonResponse personResponse = new PersonResponse();
        personResponse.getPersons().addAll(accountList);

        return ResponseEntity.status(HttpStatus.OK).body(personResponse);

    }

    @PostMapping(path = "/person/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> post(@RequestBody PersonDTO personDTO,
            @RequestHeader(name = "Authorization", required = false) String token
    ) {
        log.info("API: person/add");
        Optional<Person> account = friendService.findPersonByEmail(personDTO.getEmail());

        if (account.isPresent()) {
            log.error("Person already exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(account.get());
        }

        Person addAccount = friendService.addAccount(new Person(personDTO.getUserId(), personDTO.getName(), personDTO.getEmail()));

        if (addAccount.getId() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addAccount);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Did not find account for update");
    }

    @PostMapping(path = "/person/update/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> update(@RequestBody PersonDTO personDTO,
            @RequestHeader(name = "Authorization", required = false) String token
    ) {
        log.info("API: person/update/avatar");
        Optional<Person> account = friendService.findPersonByEmail(personDTO.getEmail());

        if (account.isPresent()) {
            log.info("Avatar: Person already exist, we can update avatar!!!");
            Person person = account.get();
            log.info("Avatar id set " + personDTO.getAvatarId());
            person.setAvatarId(personDTO.getAvatarId());
            Person updateAvatar = friendService.updateAvatar(person);
            log.info("Person after update " + updateAvatar.toString());
            return ResponseEntity.status(HttpStatus.OK).body(updateAvatar);
        }
        log.error("Person does not exist for update avatar");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Did not find account for update");
    }

    @PostMapping(path = "/person/add/friend", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> join(@RequestParam(required = true) String personId,
            @RequestParam(required = true) String friendId, @RequestHeader(name = "Authorization") String token
    ) {
        log.info("API: person/add/friend");

        boolean addFriend = friendService.addFriend(personId, friendId);

        if (addFriend) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Relationship created");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Clound not add friend");
    }
}
