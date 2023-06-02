package com.example.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "chats")
@Getter
@Setter
@RequiredArgsConstructor
public class Chat {
    @Id
    private UUID id;

    // One user can have many chats with different users
    // One user can have only one chat to one specific user
    @ManyToOne
    @JoinColumn(name = "user1", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2", nullable = false)
    private User user2;
}
