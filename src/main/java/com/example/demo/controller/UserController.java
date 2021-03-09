package com.example.demo.controller;

import com.example.demo.model.MessageResponse;
import com.example.demo.model.QRCodeGenerator;
import com.example.demo.model.RequestEmail;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.NoArgsConstructor;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@NoArgsConstructor
public class UserController {
    @Autowired
    UserRepository userRepository;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode2.png";

    @GetMapping("/all/user")
    public Boolean getAllUser(@RequestParam String emailUser) {
//        System.out.println(emailUser);
        return userRepository.findByEmail(emailUser).isPresent();
    }
    @PostMapping("/add/user")
    public ResponseEntity<?> addUser(@RequestBody RequestEmail email) {
        System.out.println(email);
   try {
       User newUser = new User();
       newUser.setEmail(email.getEmail());
       newUser.setUserId("123456789");
       System.out.println(newUser);
       userRepository.save(newUser);

       return ResponseEntity.ok().body(new MessageResponse("บันทึกเรียบร้อย"));
   } catch(Exception err) {
       return ResponseEntity.badRequest().body(new MessageResponse("บันทึกไม่สำเร็จ"));
        }
    }

    @GetMapping(value = "/genrateAndDownloadQRCode/{codeText}/{width}/{height}")
    public void download(
            @PathVariable("codeText") String codeText,
            @PathVariable("width") Integer width,
            @PathVariable("height") Integer height)
            throws Exception {
        QRCodeGenerator.generateQRCodeImage(codeText, width, height, QR_CODE_IMAGE_PATH);
    }

    @GetMapping(value = "/genrateQRCode/{codeText}/{width}/{height}")
    public ResponseEntity<byte[]> generateQRCode(
            @PathVariable("codeText") String codeText,
            @PathVariable("width") Integer width,
            @PathVariable("height") Integer height)
            throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(QRCodeGenerator.getQRCodeImage(codeText, width, height));
    }

    @RequestMapping(value = "/sid", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage() throws IOException {

        var imgFile = new ClassPathResource("monkey.jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    @Scheduled(cron = "0 58 22 1/1 * ?")
    public void sendWord() {
        Boolean allUser = userRepository.findByEmail("pongas19998@hotmail.com").isPresent();
        if(allUser) {
            System.out.println("เราพบเมลนี้");
        } else {
            System.out.println("not found");
        }

        System.out.println("Corenn!!!");
    }


}
