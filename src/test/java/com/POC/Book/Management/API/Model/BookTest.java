package com.POC.Book.Management.API.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Book book = new Book(1L, "Title", "Author", 200);

        assertEquals(1L, book.getId());
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals(200, book.getPages());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        Book book = new Book();
        book.setId(2L);
        book.setTitle("Another Title");
        book.setAuthor("Another Author");
        book.setPages(150);

        assertEquals(2L, book.getId());
        assertEquals("Another Title", book.getTitle());
        assertEquals("Another Author", book.getAuthor());
        assertEquals(150, book.getPages());
    }

    @Test
    void testEquality() {
        Book book1 = new Book(1L, "Title", "Author", 200);
        Book book2 = new Book(1L, "Title", "Author", 200);

        assertEquals(book1.getId(), book2.getId());
        assertEquals(book1.getTitle(), book2.getTitle());
        assertEquals(book1.getAuthor(), book2.getAuthor());
        assertEquals(book1.getPages(), book2.getPages());
    }
}
