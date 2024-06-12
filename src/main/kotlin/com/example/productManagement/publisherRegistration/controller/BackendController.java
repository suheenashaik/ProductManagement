package com.example.productManagement.publisherRegistration.controller;

import com.example.productManagement.publisherRegistration.entity.PublisherProfileDto;
import com.example.productManagement.publisherRegistration.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class BackendController {
    @Autowired
    private PublisherService servicre;

    @GetMapping("/viewProfile/{mobileNumber}")
    public PublisherProfileDto viewProfile(@PathVariable String mobileNumber) {
        return this.servicre.viewProfile(mobileNumber);
    }

    @PutMapping("/updateProfile")
    public String updateProfile( @RequestBody PublisherProfileDto dto) {
        return this.servicre.updateProfile(dto);
    }
}
