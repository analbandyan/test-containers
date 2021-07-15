package com.testcontainers.playground.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @EqualsAndHashCode.Include
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

    public void setBooks(Set<Book> books) {
        this.books.clear();
        addBooks(books);
    }

    public Author addBooks(Set<Book> books) {
        this.books.addAll(books);
        books.forEach(book -> book.setAuthor(this));
        return this;
    }

}
