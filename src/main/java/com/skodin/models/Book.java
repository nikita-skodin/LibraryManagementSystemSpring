package com.skodin.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "\"book\"")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Valid
    @NotEmpty(message = "Это поле должно быть заполнено")
    private String name;

    @Column(name = "author_name")
    @Valid
    @NotEmpty(message = "Это поле должно быть заполнено")
    private String authorName;

    @Column(name = "year")
    @Valid
    @Max(value = 2023, message = "Год должен быть меньше чем 2024")
    private int year;

    @Column(name = "book_taking_date")
    @Temporal(TemporalType.DATE)
    // TODO: 002 хз что по паттерну
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date bookTakingDate;

    @Transient
    private boolean isExpired;

    @ManyToOne()
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private Person owner;

    public Book() {
    }

    public Book(int id, String name, String authorName, int year) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.year = year;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getBookTakingDate() {
        return bookTakingDate;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setBookTakingDate(Date bookTakingDate) {
        this.bookTakingDate = bookTakingDate;
    }



    @Override
    public String toString() {
        return String.format("%s, %s, %d", name, authorName, year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && year == book.year && Objects.equals(name, book.name) && Objects.equals(authorName, book.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorName, year);
    }
}
