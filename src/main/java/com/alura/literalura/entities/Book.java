package com.alura.literalura.entities;

import java.util.List;
import java.util.OptionalInt;

public class Book {
    private Long id;
    private String title;
    private Author author;
    private String language;
    private Integer downloads;

    public Book() {

    }

    public Book(String title, Author author, List<String> languages, Integer downloads) {
        this.title = title;
        this.author = author;
        this.language = languages != null && !languages.isEmpty() ? String.join(",", languages) : null;
        this.downloads = OptionalInt.of(downloads).orElse(0);
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

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
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
