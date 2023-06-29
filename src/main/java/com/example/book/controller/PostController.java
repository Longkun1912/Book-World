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

    // Create post for admin
    @RequestMapping(path = "/admin/create-post", method = RequestMethod.POST)
    public String createPost(@ModelAttribute("post_create") @Valid PostHandling postHandling,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        postService.createPost(postHandling, result, redirectAttributes);
        return "redirect:/admin/community";
    }

    // Create post for user
    @RequestMapping(path = "/user/create-post", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("post_create") @Valid PostHandling postHandling,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        postService.createPost(postHandling, result, redirectAttributes);
        return "redirect:/user/community";
    }

    // Edit post for admin
    @RequestMapping(path = "/admin/edit-post/{id}", method = RequestMethod.POST)
    public String editPost(@PathVariable("id") UUID post_id,
                           @RequestParam("title") String title,
                           @RequestParam("content_image") String content_image,
                           @RequestParam("content_text") String content_text,
                           RedirectAttributes redirectAttributes){
        PostHandling postHandling = new PostHandling(post_id,title,content_text,content_image);
        postService.configureUpdatedPostBeforeSaving(postHandling,redirectAttributes);
        return "redirect:/admin/community";
    }

    // Edit post for user
    @RequestMapping(path = "/user/edit-post/{id}", method = RequestMethod.POST)
    public String updatePost(@PathVariable("id") UUID post_id,
                           @RequestParam("title") String title,
                           @RequestParam("content_image") String content_image,
                           @RequestParam("content_text") String content_text,
                           RedirectAttributes redirectAttributes){
        PostHandling postHandling = new PostHandling(post_id,title,content_text,content_image);
        postService.configureUpdatedPostBeforeSaving(postHandling,redirectAttributes);
        return "redirect:/user/community";
    }

    // Delete post for admin
    @RequestMapping(path = "/admin/delete-post/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") UUID post_id){
        postService.deletePost(post_id);
        return "redirect:/admin/community";
    }

    // Delete post for user
    @RequestMapping(path = "/user/delete-post/{id}", method = RequestMethod.GET)
    public String removePost(@PathVariable("id") UUID post_id){
        postService.deletePost(post_id);
        return "redirect:/user/community";
    }
}
