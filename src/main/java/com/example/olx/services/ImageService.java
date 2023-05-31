package com.example.olx.services;


import com.example.olx.models.Image;
import com.example.olx.repositories.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null); //тут напрямую мы не возвращаем, а перекидываем с файла в респонсе
    }


}
