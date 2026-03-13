package com.library.libraryapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.library.libraryapi.dto.ApiResponse;
import com.library.libraryapi.dto.BookDto;
import com.library.libraryapi.dto.DtoMapper;
import com.library.libraryapi.models.Book;
import com.library.libraryapi.services.BookService;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final DtoMapper dtoMapper;

    // Obtener todos los libros (con DTOs para evitar referencias circulares)
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDto>>> getBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDto> bookDtos = dtoMapper.toBookDtoList(books);
        return ResponseEntity.ok(new ApiResponse<>("Books retrieved successfully", bookDtos));
    }

    // Crear un libro
    @PostMapping
    public ResponseEntity<ApiResponse<BookDto>> createBook(@RequestBody Book book) {
        Book newBook = bookService.createBook(book);
        BookDto bookDto = dtoMapper.toBookDto(newBook);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>("Book created successfully", bookDto));
    }

    // Obtener libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        BookDto bookDto = dtoMapper.toBookDto(book);
        return ResponseEntity.ok(new ApiResponse<>("Book retrieved successfully", bookDto));
    }

    // Actualizar libro
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        BookDto bookDto = dtoMapper.toBookDto(updatedBook);
        return ResponseEntity.ok(new ApiResponse<>("Book updated successfully", bookDto));
    }

    // Eliminar libro
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new ApiResponse<>("Book deleted successfully", null));
    }

    // Buscar libros por título
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDto>>> searchBooks(@RequestParam String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        List<BookDto> bookDtos = dtoMapper.toBookDtoList(books);
        return ResponseEntity.ok(new ApiResponse<>("Books retrieved successfully", bookDtos));
    }

    // Buscar libros por autor
    @GetMapping("/search/author")
    public ResponseEntity<ApiResponse<List<BookDto>>> searchBooksByAuthor(@RequestParam String author) {
        List<Book> books = bookService.searchBooksByAuthor(author);
        List<BookDto> bookDtos = dtoMapper.toBookDtoList(books);
        return ResponseEntity.ok(new ApiResponse<>("Books retrieved successfully", bookDtos));
    }
}
