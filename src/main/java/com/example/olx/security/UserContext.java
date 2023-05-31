package com.example.olx.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserContext {
    private String email;
    private Long id;
}
