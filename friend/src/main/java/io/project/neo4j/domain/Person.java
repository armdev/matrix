/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.neo4j.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("person")
@Data
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String avatarId;

    private String name;

    private String email;

    public Person() {
    }

    @Relationship(type = "friend", direction = Relationship.Direction.OUTGOING)
    public List<Friend> friends = new ArrayList<>();

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Person(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

}
