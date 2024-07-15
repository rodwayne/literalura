package com.alura.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.model.Language;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    @Query("SELECT b FROM Book b JOIN b.author a WHERE b.title LIKE %:name%")
    Optional<Book> searchBookByName(@Param("name") String name);

    @Query("SELECT a FROM Book b JOIN b.author a WHERE a.name LIKE %:name%")
    Optional<Author> searchAuthorByName(@Param("name") String name);

    @Query("SELECT b FROM Author a JOIN a.books b")
    List<Book> searchAllBooks();

    @Query("SELECT b FROM Author a JOIN a.books b ORDER BY b.downloads DESC LIMIT 10")
    List<Book> topBooks();

    @Query("SELECT a FROM Author a WHERE a.deceaseDate > :date")
    List<Author> searchActiveAuthors(@Param("date") Integer date);

    @Query("SELECT b FROM Author a JOIN a.books b WHERE b.language = :language")
    List<Book> searchBookByLanguage(@Param("language") Language language);

}
