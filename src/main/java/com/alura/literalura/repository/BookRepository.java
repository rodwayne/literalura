package com.alura.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alura.literalura.entities.Author;
import com.alura.literalura.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findAll();

    @Query("SELECT DISTINCT b.author FROM Book b")
    List<Author> findAllAuthors();

    
}
