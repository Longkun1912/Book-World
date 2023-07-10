package com.example.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_histories")
@Getter
@Setter
@NoArgsConstructor
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private LocalDateTime track_time;

    @Column
    private String action_detail;

    @ManyToOne
    @JoinColumn(name = "user_history", nullable = false)
    private User user;
}
