/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.account.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDTO implements Serializable {

    private String name;
    private String email;
    private String userId;

    public PersonDTO(String name, String email, String userId) {
        this.name = name;
        this.email = email;
        this.userId = userId;
    }

}
