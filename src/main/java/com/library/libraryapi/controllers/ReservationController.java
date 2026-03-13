package com.library.libraryapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.library.libraryapi.dto.ApiResponse;
import com.library.libraryapi.models.Reservation;
import com.library.libraryapi.services.ReservationService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // Obtener todas las reservas
    @GetMapping
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(new ApiResponse<>("Reservations retrieved successfully", reservations));
    }

    // Crear una reserva
    @PostMapping
    public ResponseEntity<ApiResponse<Reservation>> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation newReservation = reservationService.createReservation(reservation);
            return ResponseEntity.status(201)
                    .body(new ApiResponse<>("Reservation created successfully", newReservation));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    // Obtener reservas de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByUser(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>("Reservations retrieved successfully", reservations));
    }

    // Cancelar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelReservation(@PathVariable Long id) {
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.ok(new ApiResponse<>("Reservation cancelled successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}