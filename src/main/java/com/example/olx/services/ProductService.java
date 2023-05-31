package com.example.olx.services;


import com.example.olx.models.Image;
import com.example.olx.models.Product;
import com.example.olx.repositories.ProductRepository;
import com.example.olx.security.UserContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final UserContext userContext;

    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository, UserContext userContext, UserService userService) {
        this.productRepository = productRepository;
        this.userContext = userContext;
        this.userService = userService;
    }


    @Transactional
    public List<Product> getProducts(String title) {
        if (title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }


    @Transactional
    public void saveProduct(Product product,
                            MultipartFile file1,
                            MultipartFile file2,
                            MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        log.info("Saving new Product. Title: {}; Author: {}", product.getTitle(), product.getAuthor());
        Product reserveProduct = productRepository.save(product);
        reserveProduct.setPreviewImageId(reserveProduct.getImages().get(0).getId());
        reserveProduct.setUser(userService.getById(userContext.getId()));
        productRepository.save(reserveProduct);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename().replaceAll("\\s",""));
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);

    }
}
