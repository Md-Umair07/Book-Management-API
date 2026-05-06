package com.POC.Book.Management.API.DTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidBookRequest() {
        BookRequest request = new BookRequest("Valid Title", "Valid Author", 100);
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Expected no validation errors for valid request");
    }

    @Test
    void testBlankTitle() {
        BookRequest request = new BookRequest("", "Author", 50);
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Title is mandatory")));
    }

    @Test
    void testBlankAuthor() {
        BookRequest request = new BookRequest("Title", "   ", 50);
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Author is mandatory")));
    }

    @Test
    void testPagesLessThanOne() {
        BookRequest request = new BookRequest("Title", "Author", 0);
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Pages must be greater than 0")));
    }
}

