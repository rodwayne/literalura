package com.alura.literalura.main;

import java.util.Optional;
import java.util.Scanner;

import com.alura.literalura.model.BookRecord;
import com.alura.literalura.model.ResponseRecord;
import com.alura.literalura.service.Api;
import com.alura.literalura.service.ConvertResponse;

public class Main {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private Api api = new Api();
    private ConvertResponse convertResponse = new ConvertResponse();
    private Scanner scanner = new Scanner(System.in);

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

    private void searchBook() {
        System.out.println("Enter a book to search: ");
        var name = scanner.nextLine();
        var json = api.fetch(BASE_URL + "?search=" + name.replace(" ", "%20"));
        var response = convertResponse.fetch(json, ResponseRecord.class);
        Optional<BookRecord> bookOptional = response.bookResults().stream()
                .filter(book -> book.title().toUpperCase().contains(name.toUpperCase()))
                .findFirst();

        if (bookOptional.isPresent()) {
            System.out.println("Book found");
            System.out.println(bookOptional.get());
        } else {
            System.out.println(name + " not found");
        }
    }
}
