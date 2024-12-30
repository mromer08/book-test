package com.example.test.test.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.test.test.model.Book;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void BookRepository_SaveAll_ReturnsSavedBook(){
        // Arrange
        Book book = Book.builder().title("test book").build();

        // Act
        Book savedBook = bookRepository.save(book);
        // Assert
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isGreaterThan(0);
    }
    @Test
    public void BookRepository_FindById_ReturnsBook() {
        // Arrange
        Book book = Book.builder().title("test book").build();
        Book savedBook = bookRepository.save(book);

        // Act
        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());

        // Assert
        Assertions.assertThat(retrievedBook).isPresent();
        Assertions.assertThat(retrievedBook.get().getId()).isEqualTo(savedBook.getId());
    }

    @Test
    public void BookRepository_FindById_ReturnsEmptyOptional_WhenIdDoesNotExist() {
        // Act
        Optional<Book> retrievedBook = bookRepository.findById(999L);

        // Assert
        Assertions.assertThat(retrievedBook).isEmpty();
    }

    @Test
    public void BookRepository_FindAll_ReturnsAllBooks() {
        // Arrange
        Book book1 = Book.builder().title("test book 1").build();
        Book book2 = Book.builder().title("test book 2").build();
        bookRepository.save(book1);
        bookRepository.save(book2);

        // Act
        List<Book> books = bookRepository.findAll();

        // Assert
        Assertions.assertThat(books).hasSize(2);
        Assertions.assertThat(books).extracting(Book::getTitle)
                  .containsExactlyInAnyOrder("test book 1", "test book 2");
    }

    @Test
    public void BookRepository_Delete_RemovesBook() {
        // Arrange
        Book book = Book.builder().title("test book").build();
        Book savedBook = bookRepository.save(book);

        // Act
        bookRepository.delete(savedBook);
        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());

        // Assert
        Assertions.assertThat(retrievedBook).isEmpty();
    }

    @Test
    public void BookRepository_DeleteAll_RemovesAllBooks() {
        // Arrange
        Book book1 = Book.builder().title("test book 1").build();
        Book book2 = Book.builder().title("test book 2").build();
        bookRepository.save(book1);
        bookRepository.save(book2);

        // Act
        bookRepository.deleteAll();
        List<Book> books = bookRepository.findAll();

        // Assert
        Assertions.assertThat(books).isEmpty();
    }
    
}
