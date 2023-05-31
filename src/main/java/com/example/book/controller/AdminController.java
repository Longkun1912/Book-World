package com.example.book.controller;

import com.example.book.domain.UserHandling;
import com.example.book.domain.UserInfoDetails;
import com.example.book.repository.UserRepository;
import com.example.book.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
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

    @GetMapping(value = "/add-user")
    public String addUser(Model model){
        userService.updateModel(model);
        model.addAttribute("user",new UserHandling());
        return "admin/add_user";
    }

    @PostMapping(value = "/add-user")
    public String addUserForm(@ModelAttribute("user") @Valid UserHandling userHandling, BindingResult result, Model model){
        if(result.hasErrors()){
            userService.updateModel(model);
            return "admin/add_user";
        }
        else if (userRepository.findUserByName(userHandling.getUsername()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("username",null,"Username already exists.");
            return "admin/add_user";
        }
        else if (userRepository.findUserByEmail(userHandling.getEmail()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("email",null,"Email already exists.");
            return "admin/add_user";
        }
        else if (userRepository.findUserByPhoneNumber(userHandling.getPhone_number()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("phone_number",null,"Phone number already exists.");
            return "admin/add_user";
        }
        else if(!Objects.equals(userHandling.getPassword(), userHandling.getConfirm_password())){
            userService.updateModel(model);
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "admin/add_user";
        }
        else {
            userService.saveAddUser(userHandling);
            return "redirect:/admin/user-index";
        }
    }

    @GetMapping(value = "/edit-user/{id}")
    public String editUser(@PathVariable("id") UUID user_id, Model model){
        userService.updateModel(model);
        userService.configureUserBeforeEdit(user_id, model);
        return "admin/edit_user";
    }

    @GetMapping(value = "/logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
