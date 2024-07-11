package com.alura.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.literalura.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}