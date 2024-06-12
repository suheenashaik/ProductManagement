package com.example.productManagement.publisherRegistration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublisherProfileDto {
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String address;
    private String role;

}
