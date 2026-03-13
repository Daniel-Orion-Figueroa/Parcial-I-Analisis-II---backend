package com.library.libraryapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.library.libraryapi.dto.ApiResponse;
import com.library.libraryapi.models.Loan;
import com.library.libraryapi.services.LoanService;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // Obtener todos los préstamos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Loan>>> getLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(new ApiResponse<>("Loans retrieved successfully", loans));
    }

    // Obtener préstamos de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Loan>>> getLoansByUser(@PathVariable Long userId) {
        List<Loan> loans = loanService.getLoansByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>("Loans retrieved successfully", loans));
    }

    // Crear préstamo
    @PostMapping
    public ResponseEntity<ApiResponse<Loan>> createLoan(@RequestBody Loan loan) {
        try {
            Loan newLoan = loanService.createLoan(loan);
            return ResponseEntity.status(201)
                    .body(new ApiResponse<>("Loan created successfully", newLoan));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    // Devolver préstamo
    @PutMapping("/return/{loanId}")
    public ResponseEntity<ApiResponse<Loan>> returnLoan(@PathVariable Long loanId) {
        try {
            Loan returnedLoan = loanService.returnLoan(loanId);
            return ResponseEntity.ok(new ApiResponse<>("Loan returned successfully", returnedLoan));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}