package com.example.book.controller;

import com.example.book.domain.BookDetails;
import com.example.book.entity.Category;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import com.example.book.service.BookService;
import com.example.book.service.CategoryService;
import com.example.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final BookService bookService;
    private final CategoryService categoryService;

    @RequestMapping(path = "/admin/book-index", method = RequestMethod.GET)
    public String bookIndex(Model model, @RequestParam(required = false) String title,
                            @RequestParam(required = false) Category category,
                            @RequestParam(required = false) String category_name,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) Integer recommended_age,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        userService.updateModel(model);
        List<BookDetails> filtered_books;
        // Filter params
        Optional<Category> searched_category = categoryRepository.findCategoryByName(category_name);
        if(searched_category.isPresent()){
            filtered_books = bookService.getFilteredBooks(searched_category.get(),startDate,endDate,recommended_age);
        }
        else if (title != null && !title.isEmpty()){
            filtered_books = bookService.getTitleSearchedBooks(title);
        }
        else if (author != null && !author.isEmpty()){
            filtered_books = bookService.getAuthorSearchedBooks(author);
        }
        else {
            // Filter by params (default list)
            filtered_books = bookService.getFilteredBooks(category,startDate,endDate,recommended_age);
        }
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("ages", bookService.getAge());
        model.addAttribute("books",filtered_books);
        return "admin/book_index";
    }

    @RequestMapping(path = "/admin/book-details/{id}", method = RequestMethod.GET)
    public String bookDetails(@PathVariable("id") Integer book_id, Model model){
        userService.updateModel(model);
        BookDetails book_details = bookService.getBookDetails(book_id);
        // Convert book attributes in front-end
        String book_category = book_details.getCategory().getName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String published_date = book_details.getPublished_date().format(formatter);
        String recommended_age = Integer.toString(book_details.getRecommended_age());
        String page = Integer.toString(book_details.getPage());
        model.addAttribute("book",book_details);
        model.addAttribute("book_category",book_category);
        model.addAttribute("published_date",published_date);
        model.addAttribute("recommended_age",recommended_age);
        model.addAttribute("page",page);
        return "admin/book_details";
    }


}
