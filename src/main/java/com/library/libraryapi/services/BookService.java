package com.library.libraryapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.libraryapi.models.Book;
import com.library.libraryapi.repositories.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // Obtener todos los libros
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Crear un libro
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Obtener libro por ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    // Actualizar libro
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id); // Reutiliza método que lanza excepción si no existe

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setEditorial(bookDetails.getEditorial());
        book.setPublicationYear(bookDetails.getPublicationYear());
        book.setIsbn(bookDetails.getIsbn());
        book.setCategory(bookDetails.getCategory());
        book.setTotalQuantity(bookDetails.getTotalQuantity());
        book.setAvailableQuantity(bookDetails.getAvailableQuantity());
        book.setDescription(bookDetails.getDescription());
        book.setImageUrl(bookDetails.getImageUrl());

        return bookRepository.save(book);
    }

    // Eliminar libro
    public void deleteBook(Long id) {
        Book book = getBookById(id); // Verifica que exista
        bookRepository.delete(book);
    }

    // Buscar libros por título
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // Buscar libros por autor
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByTitleContainingIgnoreCase(author);
    }

    // Método útil para reservas/préstamos: verificar disponibilidad
    public boolean isBookAvailable(Long bookId) {
        Book book = getBookById(bookId);
        return book.getAvailableQuantity() > 0;
    }

    // Reducir cantidad disponible (para reservar o prestar)
    public void decreaseAvailableQuantity(Book book) {
        if (book.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Book is not available");
        }
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);
    }

    // Aumentar cantidad disponible (para devolución o cancelación)
    public void increaseAvailableQuantity(Book book) {
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);
    }
}