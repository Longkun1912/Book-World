package com.example.book.controller;

import com.example.book.domain.UserHandling;
import com.example.book.domain.UserInfoDetails;
import com.example.book.entity.User;
import com.example.book.repository.UserRepository;
import com.example.book.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping(value = "/dashboard")
    public String adminDashboard(Model model){
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

    @PostMapping(value = "/edit-user")
    public String editUserForm(@ModelAttribute("edit_user") @Valid UserHandling userHandling, BindingResult result, Model model){
        if(result.hasErrors()){
            userService.updateModel(model);
            return "admin/edit_user";
        }
        else if (userRepository.findUserByName(userHandling.getUsername()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("username",null,"Username already exists.");
            return "admin/edit_user";
        }
        else if (userRepository.findUserByEmail(userHandling.getEmail()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("email",null,"Email already exists.");
            return "admin/edit_user";
        }
        else if (userRepository.findUserByPhoneNumber(userHandling.getPhone_number()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("phone_number",null,"Phone number already exists.");
            return "admin/edit_user";
        }
        else if(!Objects.equals(userHandling.getPassword(), userHandling.getConfirm_password())){
            userService.updateModel(model);
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "admin/edit_user";
        }
        else {
            userService.saveUpdatedUser(userHandling);
            return "redirect:/admin/user-index";
        }
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") UUID user_id){
        Optional<User> user = Optional.of(userRepository.findById(user_id).orElseThrow());
        userRepository.delete(user.get());
        return "redirect:/admin/user-index";
    }

    @GetMapping(value = "/friend-list")
    public String friendList(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String friend_name,
                             Model model){
        userService.addUserAttributesToModel(model);
        userService.configureUsersIncludeFriends(model,username,friend_name);
        return "admin/friend_list";
    }

    @GetMapping(value = "/add-friend/{id}")
    public String addFriend(@PathVariable("id") UUID user_id){
        User user = userService.addFriend(user_id);
        return "redirect:/admin/friend-list";
    }

    @GetMapping(value = "/remove-friend/{id}")
    public String removeFriend(@PathVariable("id") UUID user_id){
        User user = userService.removeFriend(user_id);
        return "redirect:/admin/friend-list";
    }
}
