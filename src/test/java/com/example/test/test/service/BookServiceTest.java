package com.example.test.test.service;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.test.test.dto.BookDto;
import com.example.test.test.model.Book;
import com.example.test.test.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void BookService_CreateBook_ReturnsBook() {
        // Arrange
        Book book = Book.builder().title("test book").build();
        BookDto bookDto = BookDto.builder().title("test book").build();

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        // Act
        Book savedBook = bookService.createBook(bookDto);

        // Assert
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getTitle()).isEqualTo("test book");
    }

    @Test
    public void BookService_GetBookById_ReturnsBook() {
        // Arrange
        Long bookId = 1L;
        Book book = Book.builder().id(bookId).title("test book").build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Book retrievedBook = bookService.getBookById(bookId);

        // Assert
        Assertions.assertThat(retrievedBook).isNotNull();
        Assertions.assertThat(retrievedBook.getId()).isEqualTo(bookId);
        Assertions.assertThat(retrievedBook.getTitle()).isEqualTo("test book");
    }

    @Test
    public void BookService_GetBookById_ThrowsException_WhenBookNotFound() {
        // Arrange
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.getBookById(bookId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Book ID doesnt exists");
    }

    @Test
    public void BookService_GetAllBooks_ReturnsListOfBooks() {
        // Arrange
        Book book1 = Book.builder().id(1L).title("test book 1").build();
        Book book2 = Book.builder().id(2L).title("test book 2").build();

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Book> books = bookService.getAllBooks();

        // Assert
        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books).hasSize(2);
        Assertions.assertThat(books).extracting(Book::getTitle)
                  .containsExactlyInAnyOrder("test book 1", "test book 2");
    }
    
}
