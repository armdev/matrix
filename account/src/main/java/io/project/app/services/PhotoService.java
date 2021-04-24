/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.services;

import io.project.app.account.dto.PersonDTO;
import io.project.app.account.httpclients.BrokerClient;
import io.project.app.domain.Photo;
import io.project.app.api.requests.FileRequest;
import io.project.app.domain.Account;
import io.project.app.repositories.PhotoRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
@Slf4j
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepo;

    @Autowired
    private BrokerClient brokerClient;
    @Autowired
    private ProfileService profileService;

    public String addPhoto(Photo photo) throws Exception {
        Optional<Photo> userFile = photoRepo.findByUserId(photo.getUserId());
        if (userFile.isPresent()) {
            log.info("We will remove old user file");
            photoRepo.delete(userFile.get());
        }
        photo = photoRepo.insert(photo);
        Optional<Account> findAccount = profileService.findAccount(photo.getUserId());
        if (findAccount.isPresent()) {
            PersonDTO personDTO = new PersonDTO(findAccount.get().getName(), findAccount.get().getEmail(), findAccount.get().getId(), photo.getId());
            brokerClient.updateAvatar(personDTO);
        }

        return photo.getId();
    }

    public FileRequest findFile(String id) {
        log.info("File id " + id);
        FileRequest fileDTO = new FileRequest();
        Optional<Photo> userFile = photoRepo.findById(id);
        if (userFile.isPresent()) {
            log.info("file is present ");

            String base64String = Base64.encodeBase64String(userFile.get().getImage().getData());
            fileDTO.setFileContent(base64String);
            log.info("file name is " + userFile.get().getFileName());
            fileDTO.setFileName(userFile.get().getFileName());
        }

        return fileDTO;

    }

    public FileRequest findFileByUserId(String userId) {
        log.info("userId  " + userId);
        FileRequest fileDTO = new FileRequest();
        Optional<Photo> userFile = photoRepo.findByUserId(userId);
        if (userFile.isPresent()) {
            log.info("file is present ");

            String base64String = Base64.encodeBase64String(userFile.get().getImage().getData());
            fileDTO.setFileContent(base64String);
            log.info("file name is " + userFile.get().getFileName());
            fileDTO.setFileName(userFile.get().getFileName());
        }

        return fileDTO;

    }

}
