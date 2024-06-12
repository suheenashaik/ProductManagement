package com.example.productManagement.customerRegistration.controller;

import com.example.productManagement.customerRegistration.entity.Customer;
import com.example.productManagement.customerRegistration.service.CustomerRegisterService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    @Autowired
    private CustomerRegisterService service;

    @GetMapping("/home")
    public String viewHomePage() {
        System.out.println("viewHomePage");
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerRegistration";
    }

    @PostMapping("/customerRegisters")
    public String registerCustomer(@ModelAttribute Customer customer, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        service.saveUser(customer, getSiteURL(request));
        return "registrationSuccess";

    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (service.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

}
