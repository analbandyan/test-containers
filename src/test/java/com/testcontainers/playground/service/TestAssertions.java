package com.testcontainers.playground.service;

import com.testcontainers.playground.model.Author;
import com.testcontainers.playground.model.Book;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TestAssertions {

    public static void assertAuthorsEqualityWithoutIdsAndBooks(Author actual, Author expected) {
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }

    public static void assertBooksHaveIds(Set<Book> actual) {
        actual.forEach(b -> assertThat(b.getId()).isNotNull());
    }

    public static void assertBooks(Set<Book> actual, Set<Book> expected) {
        assertThat(actual).hasSize(expected.size());
        actual.forEach(b -> assertBooks(b, expected));
    }

    public static void assertBooks(Book actual, Set<Book> expected) {
        final Optional<Book> matchedExpectedByIsbnOpt = expected.stream().filter(b -> b.getIsbn().equals(actual.getIsbn())).findFirst();
        assertThat(matchedExpectedByIsbnOpt).isNotNull();

        Book matchedExpectedByIsbn = matchedExpectedByIsbnOpt.get();
        assertThat(actual.getTitle()).isEqualTo(matchedExpectedByIsbn.getTitle());
    }

}
