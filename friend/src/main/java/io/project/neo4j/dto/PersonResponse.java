/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.neo4j.dto;

import io.project.neo4j.domain.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author root
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse implements Serializable {

    private List<Person> persons = new ArrayList<>();
}
