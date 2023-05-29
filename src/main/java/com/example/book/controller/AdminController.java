package com.example.book.controller;

import com.example.book.domain.UserInfoDetails;
import com.example.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping(value = "/dashboard")
    public String register(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: "+auth);

        String access_role = auth.getAuthorities().toString();
        System.out.println("Access role: "+access_role);

        if(!access_role.contains("admin")){
            return "forbidden_page";
        }
        else {
            userService.addUserAttributesToModel(model);
            return "admin/dashboard";
        }
    }

    @GetMapping(value = "/user-index")
    public String userIndex(Model model){
        userService.addUserAttributesToModel(model);
        List<UserInfoDetails> userInfoDetailsList = userService.configureUserInfo();
        model.addAttribute("users", userInfoDetailsList);
        return "admin/user_index";
    }
}
