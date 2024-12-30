package com.example.test.test.service;

import com.example.test.test.dto.BookDto;
import com.example.test.test.model.Book;
import com.example.test.test.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book createBook(BookDto bookDto){
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        return  bookRepository.save(book);
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book ID doesnt exists"));
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
