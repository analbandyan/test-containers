package com.testcontainers.playground.repository;

import com.testcontainers.playground.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByEmail(String email);

    Optional<Author> findByBooksIsbn(String isbn);

}
