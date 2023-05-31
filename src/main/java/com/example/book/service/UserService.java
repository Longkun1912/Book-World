package com.example.book.service;

import com.example.book.domain.UserHandling;
import com.example.book.domain.UserInfoDetails;
import com.example.book.domain.UserRegister;
import com.example.book.entity.Role;
import com.example.book.entity.User;
import com.example.book.repository.RoleRepository;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Set<SimpleGrantedAuthority> getRole(User user) {
        Role role = user.getRole();
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.of(userRepository.findUserByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username " + username + " not found!")));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                user.get().getPassword(), getRole(user.get()));
        return userDetails;
    }

    // Save user to database after registration
    public void saveRegisteredUser(UserRegister userRegister){
        User user = mapper.map(userRegister, User.class);
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        user.setRole(roleRepository.findRoleByName("user"));
        user.setLast_updated(LocalDateTime.now());
        userRepository.save(user);
    }

    // Save user to database after added by admin
    public void saveAddUser(UserHandling userHandling){
        User user = mapper.map(userHandling, User.class);
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(userHandling.getPassword()));
        user.setRole(roleRepository.findRoleByName(userHandling.getInput_role()));
        user.setLast_updated(LocalDateTime.now());
        userRepository.save(user);
    }

    public void configureUserBeforeEdit(UUID user_id, Model model){
        Optional<User> existing_user = Optional.of(userRepository.findById(user_id).orElseThrow());
        UserHandling userHandling = mapper.map(existing_user.get(), UserHandling.class);
        model.addAttribute("edit_user", userHandling);
    }

    // Configure user data before showing in user index
    public List<UserInfoDetails> configureUserInfo(){
        Role role = roleRepository.findRoleByName("user");
        List<User> users = userRepository.findUserByRole(role);
        List<UserInfoDetails> userInfoDetailsList = new ArrayList<>();
        for (User user : users){
            UserInfoDetails userInfoDetails = mapper.map(user, UserInfoDetails.class);
            LocalDateTime last_updated = user.getLast_updated();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy 'at' hh:mm a");
            userInfoDetails.setLast_updated(last_updated.format(formatter));
            userInfoDetailsList.add(userInfoDetails);
        }
        return userInfoDetailsList;
    }

    public List<String> getUserStatus(){
        List<String> status = new ArrayList<>();
        status.add("Enabled");
        status.add("Disabled");
        return status;
    }

    public List<String> getUserRole(){
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    public void addUserAttributesToModel(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        String role = user.getRole().getName();
        model.addAttribute("user_detail", user);
        model.addAttribute("role", role);
    }

    public void updateModel(Model model){
        addUserAttributesToModel(model);
        model.addAttribute("status",getUserStatus());
        model.addAttribute("roles", getUserRole());
    }
}
