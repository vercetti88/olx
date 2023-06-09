package com.example.olx.repositories;

import com.example.olx.models.Product;
import com.example.olx.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.parameters.P;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;




@DataJpaTest
@Slf4j
class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void findByTitle() {
        //given
        Product testProduct = Product.builder()
                .title("Title")
                .author("Abdulaziz")
                .city("Almaty")
                .price(290)
                .description("Some desc")
                .build();
        underTest.save(testProduct);

        //when
        List<Product> products = underTest.findByTitle("Title");

        log.info("Test is running");
        //then
        for(Product product : products)
            assertThat(product.getTitle()).isEqualTo("Title");
    }
}