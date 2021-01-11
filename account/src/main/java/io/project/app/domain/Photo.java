/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author root
 */
@Document(collection = "photos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo implements Serializable {

    @Id
    private String id;
    
    @Indexed(unique = true)
    private String userId;

    private Binary image;

    private String fileName;

    private String contentType;

    private Long fileSize;

    private Date uploadDate;    
    
    @LastModifiedDate
    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();

    public Photo(String fileName) {
        this.fileName = fileName;
    }

}
