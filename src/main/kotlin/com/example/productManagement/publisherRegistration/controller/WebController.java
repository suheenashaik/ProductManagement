package com.example.productManagement.publisherRegistration.controller;

import com.example.productManagement.publisherRegistration.entity.Publisher;
import com.example.productManagement.publisherRegistration.service.PublisherService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebController {

     @Autowired
     private final PublisherService publisherService;

    @GetMapping("/publisher")
    public String showRegistrationForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "publisherRegistration";
    }

    @PostMapping("/publisherRegisters")
    public String registerPublisher(@ModelAttribute Publisher publisher, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        publisherService.savePublisher(publisher, getSiteURL(request));
        return "registrationSuccess";
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
    @GetMapping("/publisher_Home")
    public String publisherHome( ) {
        return "publisher_Home";
    }

    @GetMapping("/profile")
    public String showProfile( ) {
        return "profile";
    }

    @GetMapping("/createProduct")
    public String createProduct() {
        return "create_Product";
    }
}
