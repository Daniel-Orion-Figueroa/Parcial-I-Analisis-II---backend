package com.library.libraryapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.libraryapi.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Buscar libros por título (contenga, ignorando mayúsculas)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Buscar libros por autor (contenga, ignorando mayúsculas)
    List<Book> findByAuthorContainingIgnoreCase(String author);
}