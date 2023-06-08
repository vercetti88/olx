package com.example.olx.controllers;


import com.example.olx.models.Image;
import com.example.olx.models.Product;
import com.example.olx.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Product>> products() {
        return ResponseEntity.ok(productService.getProducts(null));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Product> productInfo(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/create")
    public void createProduct(Product product, @RequestParam("files") MultipartFile[] files) throws IOException {
        productService.saveProduct(product, files);
    }

    @PostMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PatchMapping("/update/{id}")
    public void updateProduct(@PathVariable Long id,
                              Product product,
                              @RequestParam("files") MultipartFile[] files ) throws IOException {
        product.setId(id);
        productService.saveProduct(product,files);
    }

}
