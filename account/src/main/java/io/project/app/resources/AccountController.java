package io.project.app.resources;

import io.micrometer.core.annotation.Timed;
import io.project.app.domain.Photo;
import io.project.app.dto.FileRequest;
import io.project.app.services.PhotoService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author root
 */
@RestController
@RequestMapping("/api/v2/accounts")
@Slf4j
public class AccountController {

    private PhotoService photoService;

    @PutMapping
    @ResponseBody
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> put(
            @RequestBody(required = true) FileRequest fileDTO
    ) {

        // decode file byte array 
        final byte[] backToBytes = Base64.decodeBase64(fileDTO.getFileContent());

        Photo fileModel = new Photo();
        fileModel.setFileName(fileDTO.getFileName());
        fileModel.setContentType(fileDTO.getContentType());
        fileModel.setFileSize(fileDTO.getFileSize());
        fileModel.setUserId(fileDTO.getUserId());
        fileModel.setUploadDate(new Date(System.currentTimeMillis()));
        fileModel.setImage(
                new Binary(BsonBinarySubType.BINARY, backToBytes));
        log.info("File size is" + fileModel.getFileSize());
        log.info("Content type is" + fileModel.getContentType());

        String savedFile = photoService.addPhoto(fileModel);
        if (savedFile != null) {
            return ResponseEntity.status(HttpStatus.OK).body(savedFile);
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(("Could not save file"));
    }

    @GetMapping("/account/avatar")
    @ResponseBody
    @CrossOrigin
    @Timed
    public ResponseEntity<?> fetch(
            @RequestParam(name = "id", required = true) String id
    ) {

        log.info("!get  id " + id);
        final FileRequest userFile = photoService.findFile(id);

        log.info("!get user avatar file " + userFile.getFileName());

        return ResponseEntity.status(HttpStatus.OK).body(userFile);
    }

    @GetMapping("/account/avatar/user/id")
    @ResponseBody
    @CrossOrigin
    @Timed
    public ResponseEntity<?> get(
            @RequestParam(name = "userId", required = true) String userId
    ) {

        log.info("!get user id " + userId);
        final FileRequest userFile = photoService.findFileByUserId(userId);

        log.info("!get user avatar file " + userFile.getFileName());

        return ResponseEntity.status(HttpStatus.OK).body(userFile);
    }
}
