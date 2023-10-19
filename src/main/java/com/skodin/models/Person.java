package com.skodin.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "\"person\"")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Valid @NotEmpty(message = "Это поле должно быть заполнено")
    private String name;

    @Column(name = "birthday_year")
    @Valid
    @Min(value = 1850, message = "Минимальное значение дня рождения 1850 год")
    @Max(value = 2023, message = "Максимальное значение дня рождения 2023 год")
    private int birthdayYear;

    @OneToMany(mappedBy = "")
    private List<Book> books;

    public Person() {
    }

    public Person(int id, String name, int birthdayYear) {
        this.id = id;
        this.name = name;
        this.birthdayYear = birthdayYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(int birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return String.format("%s, %d", name, birthdayYear);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && birthdayYear == person.birthdayYear && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdayYear);
    }
}
