package io.project.neo4j.services;

import io.project.neo4j.domain.Friend;
import io.project.neo4j.domain.Person;
import io.project.neo4j.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FriendService {

    @Autowired
    private PersonRepository personRepository;

    public Person addAccount(Person person) {
        return personRepository.save(person);
    }

    public Optional<Person> findPerson(String personId) {
        return personRepository.findByUserId(personId);
    }

    public Optional<Person> findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public boolean addFriend(String personId, String friendId) {
        
        Optional<Person> person = personRepository.findByUserId(personId);

        Optional<Person> friend = personRepository.findByUserId(friendId);

        if (person.isPresent() && friend.isPresent()) {

            Person currentPerson = person.get();
            currentPerson.getFriends().add(new Friend(friend.get().getId(), friend.get().getUserId(), friend.get().getName(), friend.get().getEmail()));
            Person save = personRepository.save(currentPerson);

            Person currentFriend = friend.get();
            currentFriend.getFriends().add(new Friend(save.getId(), save.getUserId(), save.getName(), save.getEmail()));
            personRepository.save(currentFriend);

            return true;

        }
        return false;
    }

    public boolean removeFriend(String personId, String friendId) {
        Optional<Person> person = personRepository.findByUserId(personId);

        Optional<Person> friend = personRepository.findByUserId(friendId);

        if (person.isPresent() && friend.isPresent()) {
            Person currentPerson = person.get();
            currentPerson.getFriends().remove(new Friend(friend.get().getId()));
            Person save = personRepository.save(currentPerson);
            Person personFriend = friend.get();
            personFriend.getFriends().remove(new Friend(save.getId()));
            personRepository.save(personFriend);
            return true;
        }
        return false;
    }

}
