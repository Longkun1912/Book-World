package com.example.book.controller;

import com.example.book.domain.BookDetails;
import com.example.book.domain.UserBasicInfo;
import com.example.book.domain.UserInfoDetails;
import com.example.book.entity.User;
import com.example.book.repository.UserRepository;
import com.example.book.service.BookService;
import com.example.book.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BookService bookService;

    @GetMapping(value = "/access-denied")
    public String bannedUserPage(){
        return "user/access_denied";
    }

    @GetMapping(value = "/personal-info")
    public String viewPersonalInfo(Model model){
        userService.addUserAttributesToModel(model);
        userService.viewUserPersonalInfo(model);
        return "user/personal_info";
    }

    @GetMapping(value = "/edit-account/{id}")
    public String updatePersonalAccountForm(@PathVariable("id") UUID user_id, Model model){
        Optional<User> existing_user = userRepository.findUserById(user_id);
        if (existing_user.isPresent()){
            userService.addUserAttributesToModel(model);
            model.addAttribute("account_update", mapper.map(existing_user, UserBasicInfo.class));
            return "user/edit_account";
        }
        else {
            return "error_page";
        }
    }

    @PostMapping(value = "/edit-account")
    public String editPersonalAccount(@ModelAttribute("account_update") @Valid UserBasicInfo userBasicInfo,
                                      BindingResult result, Model model){
        if(result.hasErrors()){
            userService.addUserAttributesToModel(model);
            return "user/edit_account";
        }
        else if(!Objects.equals(userBasicInfo.getPassword(), userBasicInfo.getConfirm_password())){
            userService.updateModel(model);
            result.rejectValue("confirm_password",null,"Confirm password does not match.");
            return "user/edit_account";
        }
        else if (userRepository.findUserByEmail(userBasicInfo.getEmail()).isPresent()) {
            userService.updateModel(model);
            result.rejectValue("email",null,"Email already exists.");
            return "admin/add_user";
        }
        else {
            userService.saveUpdatedUserInfo(userBasicInfo);
            return "redirect:/user/personal-info";
        }
    }

    @GetMapping(value = "/home-page")
    public String userHome(Model model){
        userService.addUserAttributesToModel(model);
        List<BookDetails> recommended_books = bookService.getRecommendedBooksForUser();
        List<UserInfoDetails> shared_users = userService.getUserWhoShareTheSameFavorite();
        recommended_books.forEach(bookDetails -> System.out.println(bookDetails.getTitle()));
        model.addAttribute("users", shared_users);
        model.addAttribute("books", recommended_books);
        return "user/home_page";
    }

    @GetMapping(value = "/friend-list")
    public String friendList(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String friend_name,
                             Model model){
        userService.configureUsersIncludeFriends(model,username,friend_name);
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
