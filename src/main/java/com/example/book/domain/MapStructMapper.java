package com.example.book.domain;


import com.example.book.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    // User domain for registration
    UserRegister userRegister(User user);
    User userRegisterToUser(UserRegister userRegister);
}
