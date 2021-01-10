/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.repositories;

import io.project.app.domain.Photo;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
    
    Optional<Photo> findByUserId(String userId);
}
