package com.example.test.test.controller;

import com.example.test.test.dto.BookDto;
import com.example.test.test.model.Book;
import com.example.test.test.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    public void init() {
        book = Book.builder().id(1L).title("Test Book").build();
        bookDto = BookDto.builder().title("Test Book").build();
    }

    @Test
    public void BookController_CreateBook_ReturnsCreatedBook() throws Exception {
        // Arrange
        given(bookService.createBook(ArgumentMatchers.any())).willReturn(book);

        // Act
        ResultActions response = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(book.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(book.getTitle())));
    }

    @Test
    public void BookController_GetBookById_ReturnsBook() throws Exception {
        // Arrange
        given(bookService.getBookById(1L)).willReturn(book);

        // Act
        ResultActions response = mockMvc.perform(get("/book/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(book.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(book.getTitle())));
    }

    @Test
    public void BookController_GetAllBooks_ReturnsListOfBooks() throws Exception {
        // Arrange
        List<Book> books = Arrays.asList(book);
        given(bookService.getAllBooks()).willReturn(books);

        // Act
        ResultActions response = mockMvc.perform(get("/book")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(book.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", CoreMatchers.is(book.getTitle())));
    }
}
