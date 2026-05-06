package com.POC.Book.Management.API.Exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MethodArgumentNotValidException validationException;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void initService() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void handleValidationErrorsTest() {
        List<FieldError> fieldErrors = List.of(
                new FieldError("bookRequest", "title", "Title is mandatory"),
                new FieldError("bookRequest", "author", "Author is mandatory"),
                new FieldError("bookRequest", "pages", "Pages must be greater than 0")
        );

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException validationException = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(validationException.getBindingResult()).thenReturn(bindingResult);

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        var response = globalExceptionHandler.handleValidationErrors(validationException);

        assert response.getStatusCode().is4xxClientError();
        assert response.getBody().get("title").equals("Title is mandatory");
        assert response.getBody().get("author").equals("Author is mandatory");
        assert response.getBody().get("pages").equals("Pages must be greater than 0");
    }


    @Test
    public void handleNotFoundTest(){
        ResourceNotFoundException resourceNotFoundException=new ResourceNotFoundException("Book Not Found");
        globalExceptionHandler.handleNotFound(resourceNotFoundException);
    }
}
