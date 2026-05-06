package com.POC.Book.Management.API.Controller;

import com.POC.Book.Management.API.DTO.BookRequest;
import com.POC.Book.Management.API.Service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService service;

    @BeforeEach
    void initService() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createBookTest(){
        BookRequest  request = new BookRequest();
        request.setTitle("Clean Code");
        request.setAuthor("Robert Martin");
        request.setPages(464);

        bookController.createBook(request);
    }

    @Test
    public void getAllBooksTest(){
        bookController.getAllBooks();
    }

    @Test
    public void getBookByIdTest(){
        bookController.getBookById(1L);
    }

    @Test
    public void updateBookTest(){
        BookRequest  request = new BookRequest();
        request.setTitle("Clean Code");
        request.setAuthor("Robert Martin");
        request.setPages(464);

        bookController.updateBook(1L,request);
    }

    @Test
    public void deleteBookTest(){
        bookController.deleteBook(1L);
    }
}
