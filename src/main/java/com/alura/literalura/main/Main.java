package com.alura.literalura.main;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.model.BookData;
import com.alura.literalura.model.Data;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.service.Api;
import com.alura.literalura.service.ConvertResponse;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private Api api = new Api();
    private ConvertResponse convertResponse = new ConvertResponse();
    private String BASE_URL = "https://gutendex.com/books/";
    private AuthorRepository repository;

    public Main(AuthorRepository repository) {
        this.repository = repository;
    }

    public void menu() {
        var option = -1;
        var menu = """
                -----   Welcome to Literalura   -----
                -------------------------------------
                             Main Menu
                -------------------------------------
                1 - Search book by title
                2 - Search author by name
                3 - List all registered books
                4 - List registered authors
                5 - List top 10 books
                6 - List active authors
                ----------------------------------------------
                0 - Exit
                ----------------------------------------------
                Choose an option:
                """;

        while (option != 0) {
            System.out.println(menu);
            try {
                option = Integer.valueOf(scanner.nextLine());
                switch (option) {
                    case 1:
                        searchBookByTitle();
                        break;

                    case 2:
                        searchAuthorByName();
                        break;

                    case 3:
                        listBooks();
                        break;

                    case 4:
                        listAuthors();
                        break;

                    case 5:
                        topBooks();
                        break;

                    case 6:
                        listActiveAuthors();
                        break;

                    case 0:
                        System.out.println("Closing Literalura");

                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option: " + e.getMessage());
            }
        }
    }

    private void searchBookByTitle() {
        System.out.println("""
                ---------------------
                 Search book by title
                ---------------------
                 """);
        System.out.println("Enter book title: ");
        var name = scanner.nextLine();
        var json = api.fetch(BASE_URL + "?search=" + name.replace(" ", "+").toLowerCase());

        if (json.isEmpty() || !json.contains("\"count\":0,\"next\":null,\"previous\":null,\"results\":[]")) {
            var data = convertResponse.fetch(json, Data.class);

            Optional<BookData> searchedBook = data.books().stream()
                    .findFirst();

            if (searchedBook.isPresent()) {
                System.out.println(
                        "\n------------- Book  --------------" +
                                "\nTitle: " + searchedBook.get().title() +
                                "\nAuthor: " + searchedBook.get().authors().stream()
                                        .map(a -> a.name()).limit(1).collect(Collectors.joining())
                                +
                                "\nLanguage: " + searchedBook.get().languages().stream().collect(Collectors.joining()) +
                                "\n--------------------------------------\n");

                try {
                    List<Book> foundBook = searchedBook.stream().map(a -> new Book(a)).collect(Collectors.toList());
                    Author authorApi = searchedBook.stream()
                            .flatMap(b -> b.authors().stream()
                                    .map(a -> new Author(a)))
                            .collect(Collectors.toList()).stream().findFirst().get();

                    Optional<Author> authorDB = repository.searchAuthorByName(searchedBook.get().authors().stream()
                            .map(a -> a.name())
                            .collect(Collectors.joining()));

                    Optional<Book> bookOptional = repository.searchBookByName(name);

                    if (bookOptional.isPresent()) {
                        System.out.println("Book is already stored in database");
                    } else {
                        Author author;

                        if (authorDB.isPresent()) {
                            author = authorDB.get();
                            System.out.println("Author is already stored in database");
                        } else {
                            author = authorApi;
                            repository.save(author);
                        }

                        author.setBooks(foundBook);
                        repository.save(author);
                    }
                } catch (Exception e) {
                    System.out.println("Warning " + e.getMessage());
                }

            } else {
                System.out.println("Book not found");
            }
        }
    }

    public void searchAuthorByName() {
        System.out.println("""
                ---------------------
                Search author by name
                ---------------------
                 """);

        System.out.println("Enter author name: ");
        var name = scanner.nextLine();
        Optional<Author> author = repository.searchAuthorByName(name);

        if (author.isPresent()) {
            System.out.println(
                    "\n------------- Author --------------" +
                            "\nName: " + author.get().getName() +
                            "\nBirth date: " + author.get().getBirthDate() +
                            "\nDecease date: " + author.get().getDeceaseDate() +
                            "\nBooks: " + author.get().getBooks().stream()
                                    .map(b -> b.getTitle()).collect(Collectors.toList())
                            +
                            "\n--------------------------------------\n");
        } else {
            System.out.println("Author does not exist in database");
        }
    }

    public void listBooks() {
        System.out.println("""
                ---------------------
                List registered books
                ---------------------
                 """);

        List<Book> books = repository.searchAllBooks();
        books.forEach(b -> System.out.println(
                "-------------- Book -----------------" +
                        "\nTitle: " + b.getTitle() +
                        "\nAuthor: " + b.getAuthor().getName() +
                        "\nLanguage: " + b.getLanguage() +
                        "\n----------------------------------------\n"));
    }

    public void listAuthors() {
        System.out.println("""
                -----------------------
                List registered authors
                -----------------------
                 """);

        List<Author> authors = repository.findAll();
        authors.forEach(a -> System.out.println(
                "-------------- Author -----------------" +
                        "\nName: " + a.getName() +
                        "\nBirth date: " + a.getBirthDate() +
                        "\nDecease date: " + a.getDeceaseDate() +
                        "\nBooks: " + a.getBooks().stream().map(b -> b.getTitle()).collect(Collectors.toList()) +
                        "\n----------------------------------------\n"));
    }

    public void topBooks() {
        System.out.println("""
                -----------------------
                List registered authors
                -----------------------
                 """);
        List<Book> books = repository.topBooks();
        books.forEach(b -> System.out.println(
                "-------------- Book -----------------" +
                        "\nTitle: " + b.getTitle() +
                        "\nAuthor: " + b.getAuthor().getName() +
                        "\nLanguage: " + b.getLanguage() +
                        "\nDownloads: " + b.getDownloads() +
                        "\n----------------------------------------\n"));
    }

    public void listActiveAuthors() {
        System.out.println("""
                -----------------------
                List active authors
                -----------------------
                 """);

        System.out.println("Enter a year: ");

        try {
            var date = Integer.valueOf(scanner.nextLine());
            List<Author> authors = repository.searchActiveAuthors(date);

            if (!authors.isEmpty()) {
                authors.forEach(a -> System.out.println(
                "-------------- Author -----------------" +
                        "\nName: " + a.getName() +
                        "\nBirth date: " + a.getBirthDate() +
                        "\nDecease date: " + a.getDeceaseDate() +
                        "\nBooks: " + a.getBooks().stream().map(b -> b.getTitle()).collect(Collectors.toList()) +
                        "\n----------------------------------------\n"));
            } else {
                System.out.println("No active authors in " + date);
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid year: " + e.getMessage());
        }
    }

}
