/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.http.clients.friends;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Person implements Serializable {

    private Long id;

    private String userId;

    private String avatarId;

    private String name;

    private String email;

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
