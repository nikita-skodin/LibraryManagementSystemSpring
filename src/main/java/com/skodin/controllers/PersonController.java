package com.skodin.controllers;

import com.skodin.models.Book;
import com.skodin.models.Person;
import com.skodin.services.BookService;
import com.skodin.services.PersonService;
import com.skodin.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;
    private final BookService bookService;
    private final PersonValidator personValidator;

    public PersonController(PersonService personService, BookService bookService, PersonValidator personValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String allPeople(Model model) {

        model.addAttribute("people", personService.findAll());

        return "people/allPeople";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return "/people/new";
        }

        personService.save(person);

        return "redirect:/people";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @GetMapping("/{id}")
    public String personPage(Model model,
                             @PathVariable("id") int id) {

        Person person = personService.findById(id);

        List<Book> books = bookService.getBooks(person);

        model.addAttribute("personsBooks", books);
        model.addAttribute("person", person);
        return "people/personPage";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return "/people/edit";
        }

        personService.update(person, id);
        return "redirect:/people";
    }

    @PatchMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model,
                           @PathVariable("id") int id) {
        model.addAttribute("person", personService.findById(id));
        return "people/edit";
    }
}
