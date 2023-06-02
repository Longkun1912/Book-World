package com.example.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
public class Favorite {
    @Id
    private UUID id;

    @Column
    private String name;

    @Column
    private LocalDateTime created_time;

    @ManyToOne
    @JoinColumn(name = "user_favorite", nullable = false)
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "favorites")
    private List<Book> books;
}