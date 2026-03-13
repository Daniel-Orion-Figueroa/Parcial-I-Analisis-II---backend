package com.library.libraryapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.library.libraryapi.dto.ApiResponse;
import com.library.libraryapi.models.Book;
import com.library.libraryapi.services.BookService;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // Obtener todos los libros
    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(new ApiResponse<>("Books retrieved successfully", books));
    }

    // Crear un libro
    @PostMapping
    public ResponseEntity<ApiResponse<Book>> createBook(@RequestBody Book book) {
        Book newBook = bookService.createBook(book);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>("Book created successfully", newBook));
    }

    // Obtener libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(new ApiResponse<>("Book retrieved successfully", book));
    }

    // Actualizar libro
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(new ApiResponse<>("Book updated successfully", updatedBook));
    }

    // Eliminar libro
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new ApiResponse<>("Book deleted successfully", null));
    }

    // Buscar libros por título
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Book>>> searchBooks(@RequestParam String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(new ApiResponse<>("Books retrieved successfully", books));
    }

    // Buscar libros por autor
    @GetMapping("/search/author")
    public ResponseEntity<ApiResponse<List<Book>>> searchBooksByAuthor(@RequestParam String author) {
        List<Book> books = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(new ApiResponse<>("Books retrieved successfully", books));
    }
}