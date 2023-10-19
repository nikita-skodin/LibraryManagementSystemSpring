package com.skodin.controllers;

import com.skodin.models.Book;
import com.skodin.models.Person;
import com.skodin.services.BookService;
import com.skodin.services.PersonService;
import com.skodin.util.BookValidator;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static java.lang.Integer.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, PersonService personService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String allBooks(Model model,
                           @RequestParam(name = "page", required = false,
                                   defaultValue = "0")
                           Integer page,

                           @RequestParam(name = "books_per_page", required = false,
                                   defaultValue = "100") //2147483647
                           Integer booksPerPage,

                           @RequestParam(name = "sort_by_year", required = false,
                                   defaultValue = "false")
                           boolean sortByYear) {

        model.addAttribute("books", sortByYear ?
                bookService.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent() :
                bookService.findAll(PageRequest.of(page, booksPerPage)).getContent());

        return "books/allBooks";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/books/new";
        }

        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @GetMapping("/{id}")
    public String bookPage(Model model,
                           @PathVariable("id") int id) {

        Person person = bookService.findById(id).getOwner();

        model.addAttribute("returnPerson", new Person());
        model.addAttribute("owner", person);
        model.addAttribute("people", personService.findAll());

        model.addAttribute("book", bookService.findById(id));
        return "books/bookPage";
    }

    @PatchMapping("/{id}/setOwner")
    public String setOwner(@PathVariable("id") int id,
                           @ModelAttribute("returnPerson") Person returnPerson) {

        // TODO: 001 тут шаманил
        Person person = personService.findById(returnPerson.getId());
        bookService.setOwner(id, person);

        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }

        bookService.update(book, id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model,
                           @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @GetMapping("/search")
    public String searchPage(Model model) {

        model.addAttribute("book", new Book());

        return "books/search";
    }

    @GetMapping("/getBooks")
    public String getBooks(Model model,
                           @ModelAttribute("book") Book book) {

        model.addAttribute("books",
                bookService.getBooksByNameStartingWith(book.getName()));

        return "books/search";
    }

}
