package com.library.libraryapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.libraryapi.models.Loan;
import com.library.libraryapi.models.LoanStatus;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Obtener préstamos de un usuario
    List<Loan> findByUser_Id(Long userId);

    // Contar préstamos activos de un usuario (opcional para reglas futuras)
    long countByUser_IdAndStatus(Long userId, LoanStatus status);
}