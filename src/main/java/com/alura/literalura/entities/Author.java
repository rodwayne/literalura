package com.alura.literalura.entities;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private Long id;
    private String name;
    private Integer birthDate;
    private Integer deathDate;
    private List<Book> books;

    public Author(String name, Integer birthDate, Integer deathDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Integer deathDate) {
        this.deathDate = deathDate;
    }

    public List<Book> getBooks() {
        if (books == null) {
            books = new ArrayList<>();
        }

        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author: " + name + "\n" +
                "Date of birth: " + birthDate + "\n" +
                "Date of death: " + deathDate + "\n" +
                "Books: " + books.stream();
    }

}
