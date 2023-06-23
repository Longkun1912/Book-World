package com.example.book.controller;

import com.example.book.domain.UserInfoDetails;
import com.example.book.entity.User;
import com.example.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/home-page")
    public String userHome(){
        return "user/home_page";
    }

    @GetMapping(value = "/friend-list")
    public String friendList(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String friend_name,
                             Model model){
        List<UserInfoDetails> friends = userService.getFriendListByUser(friend_name);
        List<UserInfoDetails> other_users = userService.getOtherUsers(username);
        model.addAttribute("friends", friends);
        model.addAttribute("users", other_users);
        return "user/friend_list";
    }

    @GetMapping(value = "/add-friend/{id}")
    public String addFriend(@PathVariable("id") UUID user_id){
        User user = userService.addFriend(user_id);
        return "redirect:/user/friend-list";
    }

    @GetMapping(value = "/remove-friend/{id}")
    public String removeFriend(@PathVariable("id") UUID user_id){
        User user = userService.removeFriend(user_id);
        return "redirect:/user/friend-list";
    }
}
