package com.library.libraryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.libraryapi.models.Reservation;
import com.library.libraryapi.models.ReservationStatus;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Contar reservas activas de un usuario
    long countByUserIdAndStatus(Long userId, ReservationStatus status);

    // Listar reservas de un usuario
    List<Reservation> findByUserId(Long userId);
}