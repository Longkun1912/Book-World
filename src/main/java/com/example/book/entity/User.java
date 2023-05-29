package com.example.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private UUID id;

    @Column
    private String username;

    @Column
    private String image_url;

    @Column
    private String email;

    @Column
    private String phone_number;

    @Column
    private String status;

    @Column
    private LocalDateTime last_updated;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_role", nullable = false)
    private Role role;
}