package com.POC.Book.Management.API.Service;

import com.POC.Book.Management.API.Model.Book;
import com.POC.Book.Management.API.Repository.BookRepository;
import com.POC.Book.Management.API.DTO.BookRequest;
import com.POC.Book.Management.API.Exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository;

    @BeforeEach
    void initService() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook() {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setPages(464);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("Clean Code");
        savedBook.setAuthor("Robert Martin");
        savedBook.setPages(464);

        when(repository.save(any(Book.class))).thenReturn(savedBook);

        Book result = service.createBook(book);

        assertEquals("Clean Code", result.getTitle());
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book(1L, "Clean Code", "Robert Martin", 464);
        Book book2 = new Book(2L, "Effective Java", "Joshua Bloch", 416);

        when(repository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = service.getAllBooks();
        assertEquals(2, books.size());
        assertEquals("Clean Code", books.get(0).getTitle());
    }

    @Test
    void testGetBookById_Found() {
        Book book = new Book(1L, "Clean Code", "Robert Martin", 464);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        Book result = service.getBookById(1L);
        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void testGetBookById_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getBookById(99L));
    }

    @Test
    void testUpdateBook() {
        Book existing = new Book(1L, "Old Title", "Old Author", 100);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        BookRequest request = new BookRequest();
        request.setTitle("New Title");
        request.setAuthor("New Author");
        request.setPages(200);

        Book updated = new Book(1L, "New Title", "New Author", 200);
        when(repository.save(any(Book.class))).thenReturn(updated);

        Book result = service.updateBook(1L, request);

        assertEquals("New Title", result.getTitle());
        assertEquals(200, result.getPages());
    }

    @Test
    void testDeleteBook() {
        Book existing = new Book(1L, "Clean Code", "Robert Martin", 464);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        service.deleteBook(1L);

        verify(repository, times(1)).delete(existing);
    }
}
