package com.library.libraryapi.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.library.libraryapi.models.Book;
import com.library.libraryapi.models.Loan;
import com.library.libraryapi.models.LoanStatus;
import com.library.libraryapi.models.User;
import com.library.libraryapi.repositories.LoanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService; // para controlar stock de libros

    private static final int MAX_ACTIVE_LOANS = 5; // límite de préstamos activos por usuario

    // Obtener todos los préstamos
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // Obtener préstamos de un usuario
    public List<Loan> getLoansByUser(Long userId) {
        return loanRepository.findByUser_Id(userId);
    }

    // Crear préstamo con reglas de negocio
    public Loan createLoan(Loan loan) {
        User user = loan.getUser();
        Book book = bookService.getBookById(loan.getBook().getId());

        // 1️⃣ Validar límite de préstamos activos
        long activeLoans = loanRepository.countByUser_IdAndStatus(user.getId(), LoanStatus.ACTIVE);
        if (activeLoans >= MAX_ACTIVE_LOANS) {
            throw new RuntimeException("User cannot have more than " + MAX_ACTIVE_LOANS + " active loans");
        }

        // 2️⃣ Validar disponibilidad del libro
        if (book.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Book is not available for loan");
        }

        // 3️⃣ Reducir stock del libro
        bookService.decreaseAvailableQuantity(book);

        // 4️⃣ Configurar préstamo
        loan.setUser(user);
        loan.setBook(book);
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(14)); // devolución por defecto 14 días

        return loanRepository.save(loan);
    }

    // Marcar préstamo como devuelto
    public Loan returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new RuntimeException("Loan is not active");
        }

        // Actualizar estado
        loan.setStatus(LoanStatus.RETURNED);

        // Restaurar stock del libro
        bookService.increaseAvailableQuantity(loan.getBook());

        return loanRepository.save(loan);
    }
}