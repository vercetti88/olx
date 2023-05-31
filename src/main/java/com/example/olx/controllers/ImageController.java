package com.example.olx.controllers;


import com.example.olx.models.Image;
import com.example.olx.repositories.ImageRepository;
import com.example.olx.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class ImageController {

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    private final ImageService imageService;

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        //
        return ResponseEntity.ok()
                .header("fileName", URLEncoder.encode(image.getOriginalFileName(), StandardCharsets.UTF_8))
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}
