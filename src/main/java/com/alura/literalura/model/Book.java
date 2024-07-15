package com.alura.literalura.model;

import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    private Long id;
    private String title;
    private Integer downloads;
    @Enumerated(EnumType.STRING)
    @ManyToOne
    private Author author;
    private Language language;

    public Book() {

    }

    public Book(BookData bookData) {
        this.id = bookData.id();
        this.title = bookData.title();
        this.language = Language.fromString(bookData.languages().stream()
                .limit(1).collect(Collectors.joining()));
        this.downloads = bookData.downloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "\n------------ Book ------------\n" +
                "Title: " + title + "\n" +
                "Author: " + author +
                "Language: " + language +
                "\n-----------------------------------\n";
    }

}
