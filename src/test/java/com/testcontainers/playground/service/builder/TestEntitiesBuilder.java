package com.testcontainers.playground.service.builder;

import com.testcontainers.playground.model.Author;
import com.testcontainers.playground.model.Book;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestEntitiesBuilder {

    public static Author buildAuthorWithBooks(int booksCount) {
        Author author = buildAuthorWithoutBooks();
        author.setBooks(buildBooks(booksCount));
        return author;
    }

    public static Author buildAuthorWithoutBooks() {
        Author author = new Author();
        author.setName(makeUnique("Test Author"));
        author.setEmail(makeUniqueEmail());
        return author;
    }

    public static Set<Book> buildBooks(int count) {
        return IntStream.range(0, count)
                .boxed()
                .map(i -> buildBook())
                .collect(Collectors.toSet());
    }

    public static Book buildBook() {
        Book book = new Book();
        book.setTitle(makeUnique("Test book title"));
        book.setIsbn(makeUnique("000-0-00-000000-0"));
        return book;
    }

    private static String makeUnique(String text) {
        return text + ":" + randomUuid();
    }

    private static String makeUniqueEmail() {
        return "test.author" + randomUuid() + "@playgroundtestcontainers.com";
    }

    private static String randomUuid() {
        return UUID.randomUUID().toString();
    }

}
