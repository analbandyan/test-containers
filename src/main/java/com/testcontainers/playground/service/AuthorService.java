package com.testcontainers.playground.service;

import com.testcontainers.playground.model.Author;
import com.testcontainers.playground.model.Book;
import com.testcontainers.playground.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public Author addBooks(Long authorId, Set<Book> books) {
        return authorRepository.save(
                authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Book with id = " + authorId + " not found."))
                .addBooks(books)
        );
    }

    public Optional<Author> findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    public Optional<Author> findByBook(String isbn) {
        return authorRepository.findByBooksIsbn(isbn);
    }

}
