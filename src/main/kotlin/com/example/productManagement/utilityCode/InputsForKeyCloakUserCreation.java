package com.example.productManagement.utilityCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputsForKeyCloakUserCreation {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;


}

