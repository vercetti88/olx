package com.example.olx.controllers;


import com.example.olx.models.Product;
import com.example.olx.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String products(@RequestParam(name = "title", required = false) String title, Model model) {
        model.addAttribute("products", productService.getProducts(title));
        return "products";
    }

    @GetMapping ("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("images", productService.getProductById(id).getImages());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(Product product, @RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3) throws IOException {
        productService.saveProduct(product, file1, file2, file3);
        return  "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

}
