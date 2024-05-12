package org.example.resourceserver.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class ResourceController {
    @GetMapping("/cat")
    public ResponseEntity<byte[]> getImageCat() throws IOException {
        InputStream in = new FileInputStream("resource-server/cat.jpg"); //считать файл cat.jpg

        byte[] image = in.readAllBytes(); //переводим в массив байтов

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); //устанавливаем формат файла перед передачей

        //(файл(в байтах), шаблон заголовка, статус
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
