package com.alura.literalura.main;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.alura.literalura.entities.Author;
import com.alura.literalura.entities.Book;
import com.alura.literalura.model.AuthorRecord;
import com.alura.literalura.model.BookRecord;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.Api;
import com.alura.literalura.service.ConvertResponse;

public class Main {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private Api api = new Api();
    private ConvertResponse convertResponse = new ConvertResponse();
    private Scanner scanner = new Scanner(System.in);
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Main(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    private String menu = """
            1- Search book by title
            2- List registered books (not working)
            3- List registered authors (not working)
            4- List active authors within a year (not working)
            5- List books by language (not working)

            0- Salir
            """;

    public void menu() {
        var option = -1;
        while (option != 0) {
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchBook();
                    break;

                case 0:
                    System.out.println("Closing app...");
                    break;

                default:
                    System.out.println("Option does not exist. Choose a valid option");
                    break;
            }
        }
    }

    private String bookName() {
        System.out.println("Search book: ");
        return scanner.nextLine();
    }

    private void searchBook() {
        String bookName = bookName();
        Optional<Book> bookOptional = bookRepository.findAll().stream()
            .filter(b -> b.getTitle().toLowerCase().contains(bookName.toLowerCase()))
            .findFirst();

        if (bookOptional.isPresent()) {
            Book bookFound = bookOptional.get();
            System.out.println(bookFound);
        } else {
            try {
                BookRecord bookRecord = getBookRecord(bookName);
                System.out.println(bookRecord);

                if (bookRecord != null) {
                    AuthorRecord authorRecord = getAuthorRecord(bookName);
                    Author author = getOrSaveAuthor(authorRecord);

                    Book book = new Book(
                        bookRecord.title(),
                        author,
                        bookRecord.availableLanguages(),
                        bookRecord.downloads()
                    );

                    bookRepository.save(book);

                    System.out.println("Book saved succesfully");
                    System.out.println(book);
                } else {
                    System.out.println("Book not found");
                }
            } catch (Exception e) {
                System.out.println("Exception" + e.getMessage());
            }
        }
    }

    private BookRecord getBookRecord(String bookString) {
        String json = api.fetch(BASE_URL + "?search=" + bookString.replace(" ", "+"));
        return convertResponse.fetch(json, BookRecord.class);
    }

    private AuthorRecord getAuthorRecord(String bookString) {
        String json = api.fetch(BASE_URL + "?search=" + bookString.replace(" ", "+"));
        return convertResponse.fetch(json, AuthorRecord.class);
    }

    private Author getOrSaveAuthor(AuthorRecord authorRecord) {
        List<Author> authors = authorRepository.findAll();
        Optional<Author> authorOptional = authors.stream()
                .filter(a -> a.getName().equalsIgnoreCase(authorRecord.name()))
                .findFirst();

        Author author;

        if (authorOptional.isPresent()) {
            author = authorOptional.get();
        } else {
            author = new Author(authorRecord.name(), authorRecord.birthDate(), authorRecord.deathDate());
            authorRepository.save(author);
        }

        return author;
    }
}
