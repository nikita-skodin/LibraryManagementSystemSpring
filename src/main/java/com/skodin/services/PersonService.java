package com.skodin.services;

import com.skodin.models.Book;
import com.skodin.models.Person;
import com.skodin.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional()
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(Person person, int id) {
        person.setId(id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> getPersonByName(String name){
        return personRepository.getPersonByName(name);
    }

    public void setBookExpired(Book book){

    }

}
