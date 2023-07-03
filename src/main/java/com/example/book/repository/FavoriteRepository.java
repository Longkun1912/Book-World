package com.example.book.repository;

import com.example.book.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    @Query("SELECT f from Favorite f WHERE f.id =:user_id")
    Optional<Favorite> findFavoriteByUser(@Param("user_id") UUID user_id);
}
