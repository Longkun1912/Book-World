package com.example.book.service;

import com.example.book.domain.UserInfoDetails;
import com.example.book.entity.User;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    public List<UserInfoDetails> getFilteredMembers(String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        List<UserInfoDetails> member_list = new ArrayList<>();
        List<User> members = userRepository.searchUserByName(username);
        members.remove(current_user);
        System.out.println("Filtered members: " + members.size());
        return memberListResults(members, member_list);
    }

    public List<UserInfoDetails> getAllMembers(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        List<User> members = userRepository.findAll();
        members.remove(current_user);
        List<UserInfoDetails> memberDetails = new ArrayList<>();
        System.out.println("All members: " + members.size());
        return memberListResults(members,memberDetails);
    }

    public List<UserInfoDetails> memberListResults(List<User> members, List<UserInfoDetails> memberDetails){
        for (User member : members){
            UserInfoDetails converted_member = mapper.map(member, UserInfoDetails.class);
            memberDetails.add(converted_member);
        }
        return memberDetails;
    }
}
