package io.project.neo4j.repository;

import io.project.neo4j.domain.Person;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

}
