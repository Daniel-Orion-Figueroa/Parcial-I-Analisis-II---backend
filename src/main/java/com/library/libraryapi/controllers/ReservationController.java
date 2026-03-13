package com.library.libraryapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.library.libraryapi.dto.ApiResponse;
import com.library.libraryapi.dto.ReservationDto;
import com.library.libraryapi.dto.CreateReservationDto;
import com.library.libraryapi.dto.DtoMapper;
import com.library.libraryapi.models.Reservation;
import com.library.libraryapi.models.Book;
import com.library.libraryapi.models.User;
import com.library.libraryapi.services.ReservationService;
import com.library.libraryapi.services.BookService;
import com.library.libraryapi.services.UserService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final BookService bookService;
    private final UserService userService;
    private final DtoMapper dtoMapper;

    // Obtener todas las reservas (con DTOs para evitar referencias circulares)
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationDto>>> getReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        List<ReservationDto> reservationDtos = dtoMapper.toReservationDtoList(reservations);
        return ResponseEntity.ok(new ApiResponse<>("Reservations retrieved successfully", reservationDtos));
    }

    // Crear una reserva (usando DTO simple para evitar problemas)
    @PostMapping
    public ResponseEntity<ApiResponse<ReservationDto>> createReservation(@RequestBody CreateReservationDto createReservationDto) {
        try {
            // Buscar las entidades referenciadas
            Book book = bookService.getBookById(createReservationDto.getBookId());
            User user = userService.getUserById(createReservationDto.getUserId());

            // Crear la reserva con las entidades completas
            Reservation reservation = new Reservation();
            reservation.setBook(book);
            reservation.setUser(user);
            reservation.setReservationDate(java.time.LocalDate.parse(createReservationDto.getReservationDate()));
            reservation.setStatus(com.library.libraryapi.models.ReservationStatus.valueOf(createReservationDto.getStatus()));

            Reservation newReservation = reservationService.createReservation(reservation);
            ReservationDto reservationDto = dtoMapper.toReservationDto(newReservation);
            return ResponseEntity.status(201)
                    .body(new ApiResponse<>("Reservation created successfully", reservationDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    // Actualizar una reserva
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationDto>> updateReservation(@PathVariable Long id, @RequestBody CreateReservationDto createReservationDto) {
        try {
            // Buscar las entidades referenciadas
            Book book = bookService.getBookById(createReservationDto.getBookId());
            User user = userService.getUserById(createReservationDto.getUserId());

            // Crear la reserva actualizada
            Reservation reservation = new Reservation();
            reservation.setBook(book);
            reservation.setUser(user);
            reservation.setReservationDate(java.time.LocalDate.parse(createReservationDto.getReservationDate()));
            reservation.setStatus(com.library.libraryapi.models.ReservationStatus.valueOf(createReservationDto.getStatus()));

            Reservation updatedReservation = reservationService.updateReservation(id, reservation);
            ReservationDto reservationDto = dtoMapper.toReservationDto(updatedReservation);
            return ResponseEntity.ok(new ApiResponse<>("Reservation updated successfully", reservationDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    // Obtener reservas de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ReservationDto>>> getReservationsByUser(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);
        List<ReservationDto> reservationDtos = dtoMapper.toReservationDtoList(reservations);
        return ResponseEntity.ok(new ApiResponse<>("Reservations retrieved successfully", reservationDtos));
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
