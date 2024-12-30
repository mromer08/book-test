package com.example.test.test.controller;

import com.example.test.test.dto.BookDto;
import com.example.test.test.model.Book;
import com.example.test.test.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping()
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto bookDto){
        Book createdBook = bookService.createBook(bookDto);
        return ResponseEntity.ok(createdBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getResourceById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAllResources() {
        List<Book> resources = bookService.getAllBooks();
        return ResponseEntity.ok(resources);
    }
}
