package com.example.book.service;

import com.example.book.domain.BookDetails;
import com.example.book.domain.BookHandling;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final ModelMapper mapper;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public List<BookDetails> getFilteredBooks(Category category, LocalDate startDate, LocalDate endDate, Integer recommended_age){
        List<Book> filtered_books = bookRepository.filterBook(category,startDate,endDate,recommended_age);
        List<BookDetails> books = new ArrayList<>();
        return bookResults(filtered_books,books);
    }

    public List<BookDetails> getTitleSearchedBooks(String title){
        List<Book> searched_books = bookRepository.searchBookByTitle(title);
        List<BookDetails> books = new ArrayList<>();
        return bookResults(searched_books,books);
    }

    public List<BookDetails> getAuthorSearchedBooks(String author){
        List<Book> searched_books = bookRepository.searchBookByAuthor(author);
        List<BookDetails> books = new ArrayList<>();
        return bookResults(searched_books,books);
    }

    public List<BookDetails> bookResults(List<Book> books, List<BookDetails> mappedBooks){
        for (Book book : books){
            BookDetails bookDetails = mapper.map(book, BookDetails.class);
            mappedBooks.add(bookDetails);
        }
        return mappedBooks;
    }

    public void configureBookBeforeAdding(BookHandling bookHandling){
        // Convert date from string
        String published_date = bookHandling.getPublished_day();
        LocalDate date = LocalDate.parse(published_date.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        // Map book entity and save to database
        Book book = mapper.map(bookHandling, Book.class);
        book.setCategory(categoryRepository.findCategoryByName(bookHandling.getCategory_name()).get());
        book.setRecommended_age(Integer.parseInt(bookHandling.getRecommended_age()));
        book.setPublished_date(date);
        bookRepository.save(book);
    }

    public BookHandling configureBookBeforeEditing(Integer book_id){
        Optional<Book> book = Optional.of(bookRepository.findById(book_id).orElseThrow());
        return mapper.map(book.get(), BookHandling.class);
    }

    public void configureBookWhileEditing(BookHandling bookHandling){
        // Convert date from string
        String published_date = bookHandling.getPublished_day();
        LocalDate date = LocalDate.parse(published_date.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        // Map book entity and save to database
        Optional<Book> existing_book = bookRepository.findById(bookHandling.getId());
        if(existing_book.isPresent()){
            Book book = mapper.map(bookHandling, Book.class);
            book.setCategory(categoryRepository.findCategoryByName(bookHandling.getCategory_name()).get());
            book.setRecommended_age(Integer.parseInt(bookHandling.getRecommended_age()));
            book.setPublished_date(date);
            bookRepository.save(book);
        }
    }

    public void deleteBook(Integer book_id){
        Optional<Book> book = Optional.of(bookRepository.findById(book_id).orElseThrow());
        bookRepository.delete(book.get());
    }

    public BookDetails getBookDetails(Integer book_id){
        Optional<Book> book = Optional.of(bookRepository.findById(book_id).orElseThrow());
        return mapper.map(book.get(), BookDetails.class);
    }

    public List<String> getAgeToString(List<Integer> ages){
        return ages.stream().map(Objects::toString).collect(Collectors.toList());
    }

    public List<Integer> getAge(){
        List<Integer> ages = new ArrayList<>();
        ages.add(6);
        ages.add(12);
        ages.add(16);
        return ages;
    }

    public void updateBookInfoModel(Model model){
        List<String> ages = getAgeToString(getAge());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("ages", ages);
    }
}
