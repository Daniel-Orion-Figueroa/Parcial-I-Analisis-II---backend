package com.library.libraryapi.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String editorial;
    private Integer publicationYear;
    private String isbn;
    private String category;
    private Integer totalQuantity = 0;
    private Integer availableQuantity = 0;
    private String description;
    private String imageUrl;

    // Relaciones
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Loan> loans;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}
