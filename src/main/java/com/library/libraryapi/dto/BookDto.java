package com.library.libraryapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String editorial;
    private Integer publicationYear;
    private String isbn;
    private String category;
    private Integer totalQuantity;
    private Integer availableQuantity;
    private String description;
    private String imageUrl;
    
    // Constructor desde entidad
    public BookDto(com.library.libraryapi.models.Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.editorial = book.getEditorial();
        this.publicationYear = book.getPublicationYear();
        this.isbn = book.getIsbn();
        this.category = book.getCategory();
        this.totalQuantity = book.getTotalQuantity();
        this.availableQuantity = book.getAvailableQuantity();
        this.description = book.getDescription();
        this.imageUrl = book.getImageUrl();
    }
}
