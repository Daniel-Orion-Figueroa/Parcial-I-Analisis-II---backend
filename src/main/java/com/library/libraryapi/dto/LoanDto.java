package com.library.libraryapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long id;
    private String loanDate;
    private String returnDate;
    private String status;
    private UserDto user;
    private BookDto book;
    
    // Constructor desde entidad
    public LoanDto(com.library.libraryapi.models.Loan loan) {
        this.id = loan.getId();
        this.loanDate = loan.getLoanDate() != null ? loan.getLoanDate().toString() : null;
        this.returnDate = loan.getReturnDate() != null ? loan.getReturnDate().toString() : null;
        this.status = loan.getStatus() != null ? loan.getStatus().toString() : null;
        
        // Convertir entidades anidadas a DTOs para evitar referencias circulares
        if (loan.getUser() != null) {
            this.user = new UserDto(loan.getUser());
        }
        
        if (loan.getBook() != null) {
            this.book = new BookDto(loan.getBook());
        }
    }
}
