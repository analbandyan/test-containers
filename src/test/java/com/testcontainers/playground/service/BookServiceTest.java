package com.testcontainers.playground.service;

import com.testcontainers.playground.AbstractIntegrationTest;
import com.testcontainers.playground.model.Author;
import com.testcontainers.playground.model.Book;
import com.testcontainers.playground.repository.AuthorRepository;
import com.testcontainers.playground.service.builder.TestEntitiesBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class BookServiceTest implements AbstractIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void findBooksByAuthorIdNotExisting() {
        final Set<Book> books = bookService.findBooksByAuthor(-1L);
        assertThat(books).isEmpty();
    }

    @Test
    void findBooksByAuthorIdExisting() {
        final Author author = TestEntitiesBuilder.buildAuthorWithBooks(3);
        final Set<Book> builtBooks = author.getBooks();

        final Long authorId = authorRepository.save(author).getId();

        final Set<Book> foundBooks = bookService.findBooksByAuthor(authorId);

        assertThat(foundBooks).isNotNull();
        assertThat(foundBooks).isNotEmpty();

        TestAssertions.assertBooks(foundBooks, builtBooks);
    }

}
