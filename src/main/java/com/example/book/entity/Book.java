package com.example.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@RequiredArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private LocalDate published_date;

    @Column
    private int page;

    @Column
    private String description;

    @Column
    private String image_url;

    @Column
    private String content;

    @Column
    private int recommended_age;

    @ManyToOne
    @JoinColumn(name = "book_category", nullable = false)
    private Category category;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "book_favorite_details",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "favorite_id")
    )
    private List<Favorite> favorites;
}
