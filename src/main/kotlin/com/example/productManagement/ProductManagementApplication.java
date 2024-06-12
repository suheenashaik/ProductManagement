package com.example.productManagement;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication(exclude = OAuth2ClientAutoConfiguration.class)
public class ProductManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductManagementApplication.class,args);
        System.out.println("hi suheena");
    }
}
