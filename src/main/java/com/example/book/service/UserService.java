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

    public void saveUpdatedUser(UserHandling userHandling){
        Optional<User> existed_user = userRepository.findById(userHandling.getId());
        if(existed_user.isPresent()){
            User updated_user = mapper.map(userHandling, User.class);
            updated_user.setId(userHandling.getId());
            updated_user.setRole(roleRepository.findRoleByName(userHandling.getInput_role()));
            updated_user.setLast_updated(LocalDateTime.now());
            updated_user.setPassword(passwordEncoder.encode(userHandling.getPassword()));
            userRepository.save(updated_user);
        }
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

    //Show friend list of a current logged user
    public List<UserInfoDetails> getFriendListByUser(String friend_name){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        List<User> friends = new ArrayList<>();
        if (friend_name != null && !friend_name.isEmpty()){
            friends = userRepository.searchFriendListByUser(user.getId(),friend_name);
        }
        else {
            friends = userRepository.findFriendListByUser(user.getId());
        }
        List<UserInfoDetails> friend_details = new ArrayList<>();
        for (User friend : friends){
            UserInfoDetails friend_detail = mapper.map(friend, UserInfoDetails.class);
            friend_detail.setRole_name(friend.getRole().getName());
            friend_details.add(friend_detail);
        }
        return friend_details;
    }

    // Show other users of the system who are not friend of the logged user
    public List<UserInfoDetails> getOtherUsers(String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        List<User> users = new ArrayList<>();
        if (username != null && !username.isEmpty()){
            users = userRepository.searchNonFriendUsersByUserIdAndLoggedInUserId(current_user.getId(), username);
        }
        else{
            users = userRepository.findNonFriendUsersByUserIdAndLoggedInUserId(current_user.getId());
        }
        List<UserInfoDetails> system_users = new ArrayList<>();
        for (User user : users){
            UserInfoDetails userInfoDetails = mapper.map(user, UserInfoDetails.class);
            userInfoDetails.setRole_name(user.getRole().getName());
            system_users.add(userInfoDetails);
        }
        return system_users;
    }

    public User addFriend(UUID friend_id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        User friend = userRepository.findUserById(friend_id).orElseThrow();
        current_user.getFriends().add(friend);
        return userRepository.save(current_user);
    }

    public User removeFriend(UUID friend_id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        User friend = userRepository.findUserById(friend_id).orElseThrow();
        current_user.getFriends().remove(friend);
        return userRepository.save(current_user);
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
