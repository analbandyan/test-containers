package com.testcontainers.playground.service;

import com.testcontainers.playground.model.Book;
import com.testcontainers.playground.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public Set<Book> findBooksByAuthor(Long authorId) {
        return bookRepository.findBooksByAuthorId(authorId);
    }

}
