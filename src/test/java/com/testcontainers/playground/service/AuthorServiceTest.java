package com.testcontainers.playground.service;

import com.testcontainers.playground.AbstractIntegrationTest;
import com.testcontainers.playground.model.Author;
import com.testcontainers.playground.model.Book;
import com.testcontainers.playground.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.testcontainers.playground.service.TestAssertions.*;
import static com.testcontainers.playground.service.builder.TestEntitiesBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class AuthorServiceTest implements AbstractIntegrationTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void createAuthorWithoutBooks() {
        final Author builtAuthor = buildAuthorWithoutBooks();
        final Author resultAuthor = authorService.createAuthor(builtAuthor);

        assertThat(resultAuthor).isNotNull();
        assertThat(resultAuthor.getId()).isNotNull();
        assertAuthorsEqualityWithoutIdsAndBooks(resultAuthor, builtAuthor);

        final Optional<Author> authorFromDbOpt = authorRepository.findById(resultAuthor.getId());
        assertThat(authorFromDbOpt).isNotEmpty();
        final Author authorFromDb = authorFromDbOpt.get();

        assertAuthorsEqualityWithoutIdsAndBooks(authorFromDb, resultAuthor);
    }

    @Test
    void createAuthorWithBooks() {
        final Author builtAuthor = buildAuthorWithBooks(2);
        final Author resultAuthor = authorService.createAuthor(builtAuthor);

        assertThat(resultAuthor).isNotNull();
        assertThat(resultAuthor.getId()).isNotNull();
        assertAuthorsEqualityWithoutIdsAndBooks(resultAuthor, builtAuthor);
        assertBooksHaveIds(resultAuthor.getBooks());
        assertBooks(resultAuthor.getBooks(), builtAuthor.getBooks());

        final Optional<Author> authorFromDbOpt = authorRepository.findById(resultAuthor.getId());
        assertThat(authorFromDbOpt).isNotEmpty();
        final Author authorFromDb = authorFromDbOpt.get();

        assertAuthorsEqualityWithoutIdsAndBooks(authorFromDb, resultAuthor);
        assertBooks(authorFromDb.getBooks(), builtAuthor.getBooks());
    }

    @Test
    void addBooksToAuthorWithoutBooks() {
        final Long authorId = authorRepository.save(buildAuthorWithoutBooks()).getId();
        final Set<Book> booksToAdd = buildBooks(2);
        final Author authorWithAddedBooks = authorService.addBooks(authorId, booksToAdd);
        final Set<Book> addedBooks = authorWithAddedBooks.getBooks();

        assertBooksHaveIds(addedBooks);
        assertBooks(addedBooks, booksToAdd);

        final Set<Book> booksFromDb = authorRepository.getById(authorId).getBooks();

        assertBooks(booksFromDb, booksToAdd);
    }

    @Test
    void addBooksToAuthorWithBooks() {
        final Author author = buildAuthorWithBooks(2);
        final Set<Book> expectedBuiltBooks = new HashSet<>(author.getBooks());
        final Long authorId = authorRepository.save(author).getId();
        final Set<Book> booksToAdd = buildBooks(2);
        expectedBuiltBooks.addAll(booksToAdd);

        final Author authorWithAddedBooks = authorService.addBooks(authorId, booksToAdd);
        final Set<Book> actualResultBooks = authorWithAddedBooks.getBooks();

        assertBooksHaveIds(actualResultBooks);
        assertBooks(actualResultBooks, expectedBuiltBooks);

        final Set<Book> booksFromDb = authorRepository.getById(authorId).getBooks();

        assertBooks(booksFromDb, expectedBuiltBooks);
    }

    @Test
    void findByEmailNotExisting() {
        final Optional<Author> author = authorService.findByEmail("not.existing@email.test");
        assertThat(author).isEmpty();
    }

    @Test
    void findByEmailExisting() {
        final Author builtAuthor = buildAuthorWithoutBooks();
        authorRepository.save(builtAuthor);
        final Optional<Author> authorResultOpt = authorService.findByEmail(builtAuthor.getEmail());
        assertThat(authorResultOpt).isNotEmpty();

        final Author authorResult = authorResultOpt.get();
        assertThat(authorResult.getId()).isNotNull();

        assertAuthorsEqualityWithoutIdsAndBooks(authorResult, builtAuthor);
    }

    @Test
    void findByBookNotExisting() {
        authorRepository.save(buildAuthorWithBooks(2));
        final Optional<Author> author = authorService.findByBook("not existing isbn");
        assertThat(author).isEmpty();
    }

    @Test
    void findByBookExisting() {
        final Author builtAuthor1 = buildAuthorWithBooks(2);
        final Long authorId = authorRepository.save(builtAuthor1).getId();

        final Author builtAuthor2 = buildAuthorWithBooks(2);
        authorRepository.save(builtAuthor2);

        final String isbn = builtAuthor1.getBooks().iterator().next().getIsbn();
        final Optional<Author> authorOpt = authorService.findByBook(isbn);
        assertThat(authorOpt).isNotEmpty();

        final Author author = authorOpt.get();
        assertThat(author.getId()).isEqualTo(authorId);
    }

}
