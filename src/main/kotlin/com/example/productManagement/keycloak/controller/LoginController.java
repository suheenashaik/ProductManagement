package com.example.productManagement.keycloak.controller;

import com.example.productManagement.keycloak.dtos.LoginRequestDto;
import com.example.productManagement.keycloak.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    @Autowired
   private LoginService loginService;

    @GetMapping("/login")
    public String loginUser(Model model) {
        model.addAttribute("userLoginDto", new LoginRequestDto());
        return "login";
    }

    @PostMapping("/userLogin")
    public String loginUser(@ModelAttribute LoginRequestDto loginDto, HttpServletResponse res)throws IOException {

        String role=this.loginService.login(loginDto,res);
        if(role.equals("org-customer")) {
            return "customer_Home";
        }
        else if(role.equals("org-publisher")){
            return "publisher_Home";
        }
        else{
            return "login_fail";
        }
    }
}
