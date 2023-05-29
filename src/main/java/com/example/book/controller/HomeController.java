package com.example.book.controller;

import com.example.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    @GetMapping(value = "/")
    public String homeIndex(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication){
        if(authentication != null){
            final UserDetails userDetails = userService
                    .loadUserByUsername(authentication.getName());
            System.out.println("Account logged in: "+userDetails);

            String role = authentication.getAuthorities().toString();
            System.out.println("User role: "+role);

            if(role.contains("admin")){
                return "redirect:/admin/dashboard";
            }
            else if (role.contains("user")){
                return "redirect:/user/home-page";
            }
            else{
                return "error_page";
            }
        } else {
            return "error_page";
        }
    }
}
