package com.example.book.service;

import com.example.book.domain.BookDetails;
import com.example.book.entity.Book;
import com.example.book.entity.Favorite;
import com.example.book.entity.User;
import com.example.book.repository.BookRepository;
import com.example.book.repository.FavoriteRepository;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final BookRepository bookRepository;

    public Set<BookDetails> viewBooksInFavorite(){
        Favorite favorite = checkUserFavoriteIfExistForCurrentUser();
        return favorite.getBooks()
                .stream()
                .map(book -> mapper.map(book, BookDetails.class))
                .collect(Collectors.toSet());
    }

    public Favorite addBookToFavorite(Integer book_id){
        Favorite favorite = checkUserFavoriteIfExistForCurrentUser();
        Book book = bookRepository.findById(book_id).orElseThrow();
        favorite.getBooks().add(book);
        return favoriteRepository.save(favorite);
    }

    public Favorite removeBookFromFavorite(Integer book_id){
        Favorite favorite = checkUserFavoriteIfExistForCurrentUser();
        Book book = bookRepository.findById(book_id).orElseThrow();
        favorite.getBooks().remove(book);
        return favoriteRepository.save(favorite);
    }

    public Favorite checkUserFavoriteIfExistForCurrentUser(){
        Favorite favorite;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        Optional<Favorite> existing_favorite = favoriteRepository.findFavoriteByUser(user);
        if (existing_favorite.isEmpty()){
            favorite = new Favorite(user);
            favorite.setId(UUID.randomUUID());
            favoriteRepository.save(favorite);
        }
        else {
            favorite = existing_favorite.get();
        }
        return favorite;
    }
}
