package com.example.book.domain;

import com.example.book.security.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRegister {
    @JsonProperty
    @Size(min = 2, max = 100, message = "Username name must be between 2 and 100 characters.")
    private String username;

    @JsonProperty
    private MultipartFile image_file;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    @Email
    private String email;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    private String phone_number;

    @JsonProperty
    @ValidPassword
    @NotEmpty(message = "This field cannot be empty.")
    private String password;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    private String confirm_password;
}
