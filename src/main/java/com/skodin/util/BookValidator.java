package com.skodin.util;

import com.skodin.models.Book;
import com.skodin.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    // TODO: 001 переписать валидатор

    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Book book = (Book) target;

        if(bookService.getBooksByName(book.getName()).isPresent()){
            errors.rejectValue("name", "", "Такая книга уже существует");

        }

    }
}
