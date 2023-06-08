package com.example.olx.services;

import com.example.olx.models.Product;
import com.example.olx.models.User;
import com.example.olx.repositories.ProductRepository;
import com.example.olx.security.UserContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {


    private ProductService underTest;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserContext userContext;
    @Mock
    private UserService userService;



    @BeforeEach
    void setUp() {
        underTest = new ProductService(productRepository,userContext,userService);
    }


    @Test
    void canGetProducts() {
        //when
        underTest.getProducts(null);
        //then
        verify(productRepository).findAll();
    }


    @Test
    void CanSaveProduct() throws IOException {
        // given
        Product testProduct = Product.builder()
                .title("Title")
                .author("Abdulaziz")
                .city("Almaty")
                .price(290)
                .description("Some desc")
                .build();
        //when
        underTest.saveProduct(testProduct);
        //then
        ArgumentCaptor<Product> productArgumentCaptor =
                ArgumentCaptor.forClass(Product.class);

        verify(productRepository).
                save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();

        assertThat(capturedProduct).isEqualTo(testProduct);
    }

    @Test
    void setAttributesToImages() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void getProductById() {
    }
}