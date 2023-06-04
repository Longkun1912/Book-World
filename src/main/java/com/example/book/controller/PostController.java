package com.example.book.controller;

import com.example.book.domain.PostHandling;
import com.example.book.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @RequestMapping(path = "/admin/create-post", method = RequestMethod.POST)
    public String createPost(@ModelAttribute("post_create") @Valid PostHandling postHandling, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()){
            String message = "Error while creating new post. Validation failed.";
            System.out.println(message);
            redirectAttributes.addFlashAttribute("message", message);
        } else {
            postService.saveNewPost(postHandling);
        }
        return "redirect:/admin/community";
    }

    @RequestMapping(path = "/admin/edit-post/{id}", method = RequestMethod.POST)
    public String editPost(@PathVariable("id") UUID post_id,
                           @RequestParam("title") String title,
                           @RequestParam("content_text") String content_text){
        postService.saveUpdatedPost(post_id,title,content_text);
        return "redirect:/admin/community";
    }

    @RequestMapping(path = "/admin/delete-post/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") UUID post_id){
        postService.deletePost(post_id);
        return "redirect:/admin/community";
    }
}
