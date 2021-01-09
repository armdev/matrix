/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.neo4j.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class PersonDTO implements Serializable{
    
    private String name;
    private String email;
    
}
