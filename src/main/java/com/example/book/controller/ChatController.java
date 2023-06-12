package com.example.book.controller;

import com.example.book.domain.MessageDetails;
import com.example.book.domain.UserInfoDetails;
import com.example.book.entity.Chat;
import com.example.book.entity.Message;
import com.example.book.entity.User;
import com.example.book.service.ChatService;
import com.example.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;
    private final ChatService chatService;

    @RequestMapping(value = "/admin/message-index", method = RequestMethod.GET)
    public String chatList(Model model,@RequestParam(required = false) String username){
        userService.addUserAttributesToModel(model);
        User last_member = chatService.getLastContactMember();
        List<UserInfoDetails> members;
        if (username != null && !username.isEmpty()){
            members = chatService.getFilteredMembers(username);
        }
        else {
            members = chatService.getAllMembers();
        }
        List<MessageDetails> messages = chatService.getMessageInChat(last_member.getId());
        model.addAttribute("messages", messages);
        model.addAttribute("members", members);
        model.addAttribute("member_uid", last_member.getId());
        return "admin/message_index";
    }

    @RequestMapping(value = "/admin/message-box/{id}", method = RequestMethod.GET)
    public String messageBox(Model model, @PathVariable(value = "id") UUID member_id,
                             @RequestParam(required = false) String username,
                             RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message","");
        userService.addUserAttributesToModel(model);
        List<UserInfoDetails> members = (username != null && !username.isEmpty()) ? chatService.getFilteredMembers(username) : chatService.getAllMembers();
        List<MessageDetails> messages = chatService.getMessageInChat(member_id);
        model.addAttribute("messages", messages);
        model.addAttribute("members", members);
        model.addAttribute("member_uid", member_id);
        return "admin/inbox";
    }

    @RequestMapping(value = "/admin/open-chat/{id}", method = RequestMethod.GET)
    public String openMessage(@PathVariable("id") UUID user_id){
        Chat chat = chatService.startAChat(user_id);
        return "redirect:/admin/message-box/" + user_id;
    }

    @RequestMapping(value = "/admin/send-message/{id}", method = RequestMethod.POST)
    public String sendMessage(@PathVariable("id") UUID member_uid,
                              @RequestParam("message_text") String message_text,
                              RedirectAttributes redirectAttributes){
        if (message_text.isEmpty()){
            redirectAttributes.addFlashAttribute("message","This field cannot be empty.");
        }
        else {
            Message message = chatService.saveMessage(member_uid, message_text);
        }
        return "redirect:/admin/message-box/" + member_uid;
    }

    @RequestMapping(value = "/admin/delete-chat/{id}", method = RequestMethod.GET)
    public String deleteChat(@PathVariable("id") UUID chat_id){
        chatService.deleteChat(chat_id);
        return "redirect:/admin/message-index";
    }

    @RequestMapping(value = "/admin/edit-message/{id}", method = RequestMethod.POST)
    public String editMessage(@PathVariable("id") UUID message_id, @RequestParam("message_text") String message_text){
        System.out.println("Edit message id: "+ message_id);
        Message message = chatService.updateMessage(message_id, message_text);
        return "redirect:/admin/message-index";
    }

    @RequestMapping(value = "/admin/delete-message/{id}", method = RequestMethod.GET)
    public String deleteMessage(@PathVariable("id") UUID message_id){
        System.out.println("Delete message id: " + message_id);
        chatService.deleteMessage(message_id);
        return "redirect:/admin/message-index";
    }
}
