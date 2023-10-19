package com.skodin.repositories;

import com.skodin.models.Book;
import com.skodin.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> getBooksByOwner(Person owner);

    Optional<Book> getBooksByName(String name);

    List<Book> getBooksByNameStartingWith(String name);

}
