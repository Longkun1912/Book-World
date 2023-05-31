package com.example.book.repository;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b from Book b WHERE (b.category =:category)")
    List<Book> findBookByCategory(@Param("category") Category category);

    @Query("SELECT b from Book b WHERE b.title =:title")
    Optional<Book> findBookByTitle(@Param("title") String title);

    @Query("SELECT b from Book b WHERE (b.title LIKE CONCAT('%', COALESCE(:title, ''), '%')" +
            "OR :title IS NULL)")
    List<Book> searchBookByTitle(@Param("title") String title);

    @Query("SELECT b from Book b WHERE (b.author LIKE CONCAT('%', COALESCE(:author, ''), '%')" +
            "OR :author IS NULL)")
    List<Book> searchBookByAuthor(@Param("author") String author);

    @Query("SELECT b from Book b WHERE (:category is null OR b.category = :category) " +
            "AND (((cast(:startDate as LocalDate) is null ) and (cast(:endDate as LocalDate) is null )) OR (CAST(b.published_date AS LocalDate) >= :startDate AND CAST(b.published_date AS LocalDate) <= :endDate))" +
            "AND (:recommended_age is null OR b.recommended_age >= :recommended_age)")
    List<Book> filterBook(@Param("category") Category category,
                          @Param("startDate") LocalDate startDate,
                          @Param("endDate") LocalDate endDate,
                          @Param("recommended_age") Integer recommended_age);
}
