package com.POC.Book.Management.API.Controller;

import com.POC.Book.Management.API.DTO.BookRequest;
import com.POC.Book.Management.API.Model.Book;
import com.POC.Book.Management.API.Service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @TestConfiguration
    static class MockConfig {
        @Bean
        public BookService bookService() {
            return Mockito.mock(BookService.class);
        }
    }

    @Autowired
    private BookService bookService;

    @Test
    void testCreateBook() throws Exception {
        BookRequest request = new BookRequest("Integration Title", "Integration Author", 250);
        Book savedBook = new Book(1L, "Integration Title", "Integration Author", 250);

        Mockito.when(bookService.createBook(Mockito.any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Integration Title")))
                .andExpect(jsonPath("$.author", is("Integration Author")))
                .andExpect(jsonPath("$.pages", is(250)));
    }

    @Test
    void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1L, "Book One", "Author One", 100),
                new Book(2L, "Book Two", "Author Two", 150)
        );

        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].title", is("Book One")))
                .andExpect(jsonPath("$[1].title", is("Book Two")));
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = new Book(1L, "Book One", "Author One", 100);

        Mockito.when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Book One")));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookRequest request = new BookRequest("Updated Title", "Updated Author", 300);
        Book updatedBook = new Book(1L, "Updated Title", "Updated Author", 300);

        Mockito.when(bookService.updateBook(Mockito.eq(1L), Mockito.any(BookRequest.class)))
                .thenReturn(updatedBook);

        mockMvc.perform(put("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.author", is("Updated Author")))
                .andExpect(jsonPath("$.pages", is(300)));
    }

    @Test
    void testDeleteBook() throws Exception {
        Mockito.doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isNoContent());
    }
}
