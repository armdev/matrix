/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.neo4j.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend implements Serializable {

    private static final long serialVersionUID = 5962773304041441276L;

    @Id
    private Long id;    
    private String userId;
    private String name;
    private String email;

    public Friend(Long id) {
        this.id = id;
    }

}
