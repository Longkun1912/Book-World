package com.example.book.controller;

import com.example.book.domain.UserInfoDetails;
import com.example.book.service.ChatService;
import com.example.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;
    private final ChatService chatService;

    @RequestMapping(value = "/admin/message-index", method = RequestMethod.GET)
    public String chatList(Model model,@RequestParam(required = false) String username){
        userService.addUserAttributesToModel(model);
        List<UserInfoDetails> members;
        if (username != null && !username.isEmpty()){
            members = chatService.getFilteredMembers(username);
        }
        else {
            members = chatService.getAllMembers();
        }
        model.addAttribute("members", members);
        return "admin/message_index";
    }
}
