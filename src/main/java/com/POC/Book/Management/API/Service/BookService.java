package com.POC.Book.Management.API.Service;

import com.POC.Book.Management.API.DTO.BookRequest;
import com.POC.Book.Management.API.Exception.ResourceNotFoundException;
import com.POC.Book.Management.API.Model.Book;
import com.POC.Book.Management.API.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public Book createBook(Book book) {
        return repository.save(book);
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    public Book updateBook(Long id, BookRequest request) {
        Book book = getBookById(id);
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPages(request.getPages());
        return repository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        repository.delete(book);
    }
}
