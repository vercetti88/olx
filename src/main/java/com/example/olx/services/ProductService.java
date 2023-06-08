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
    public void saveProduct(Product product, MultipartFile[] files) throws IOException {
        setAttributesToImages(files, product);
        log.info("Saving new Product. Title: {}; Author: {}", product.getTitle(), product.getAuthor());
        Product reserveProduct = productRepository.save(product);
        reserveProduct.setPreviewImageId(reserveProduct.getImages().get(0).getId());
        reserveProduct.setUser(userService.getById(userContext.getId()));
        productRepository.save(reserveProduct);
    }
    @Transactional
    public void saveProduct(Product product) throws IOException {
        log.info("Saving new Product. Title: {}; Author: {}", product.getTitle(), product.getAuthor());
        product.setUser(userService.getById(userContext.getId()));
        productRepository.save(product);
    }

    public void setAttributesToImages (MultipartFile[] files, Product product) throws IOException {
        for(int i = 0; i < files.length && files[0].getSize() !=0; i++) {
            Image image = toImageEntity(files[i]);
            product.addImageToProduct(image);
            if( i == 0 ) image.setPreviewImage(true);
        }
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
