package com.alura.literalura.entities;

import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;
    private String language;
    private Double downloads;

    public Book() {

    }

    public Book(String title, Author author, List<String> languages, Double downloads) {
        this.title = title;
        this.author = author;
        this.language = languages != null && !languages.isEmpty() ? String.join(",", languages) : null;
        this.downloads = OptionalDouble.of(downloads).orElse(0);
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Author: " + author.getName() + "\n" +
                "Language: " + language + "\n" +
                "Downloads: " + downloads;
    }

}
