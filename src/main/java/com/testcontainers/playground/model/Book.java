package com.testcontainers.playground.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Author author;

    private String title;

    @EqualsAndHashCode.Include
    private String isbn;

}
