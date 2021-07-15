package com.testcontainers.playground.repository;

import com.testcontainers.playground.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Set<Book> findBooksByAuthorId(Long authorId);

}
