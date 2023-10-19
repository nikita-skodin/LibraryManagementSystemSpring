package com.skodin.services;

import com.skodin.models.Book;
import com.skodin.models.Person;
import com.skodin.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Page<Book> findAll(Pageable var1) {
        return bookRepository.findAll(var1);
    }

    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional()
    public void save(Book book) {
        bookRepository.save(book);
    }

    public List<Book> getBooks(Person person) {
        List<Book> books = bookRepository.getBooksByOwner(person);

        for (Book book : books) {
            book.setExpired(compareDate(book.getBookTakingDate(), new Date()));
        }

        return books;
    }

    private boolean compareDate(Date date1, Date date2){

        Date tenDays = new Date(864000000L);

        return date2.getTime() - date1.getTime() > tenDays.getTime();
    }

    @Transactional
    public void update(Book book, int id) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void setOwner(int id, Person owner) {

        Book book = findById(id);

        if (owner == null){
            book.setBookTakingDate(null);
        } else {
            book.setBookTakingDate(new Date());
        }

        book.setOwner(owner);

    }

    public Optional<Book> getBooksByName(String name){
        return bookRepository.getBooksByName(name);
    }

    public List<Book> getBooksByNameStartingWith(String name){
       return bookRepository.getBooksByNameStartingWith(name);
    }

}
