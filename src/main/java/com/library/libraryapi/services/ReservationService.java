package com.library.libraryapi.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.library.libraryapi.models.Book;
import com.library.libraryapi.models.Reservation;
import com.library.libraryapi.models.ReservationStatus;
import com.library.libraryapi.models.User;
import com.library.libraryapi.repositories.ReservationRepository;
import com.library.libraryapi.services.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookService bookService;

    // Obtener todas las reservas
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Crear reserva con reglas de negocio
    public Reservation createReservation(Reservation reservation) {

        User user = reservation.getUser();
        Long userId = user.getId();

        // 1️⃣ Validar límite de reservas activas
        long totalReservations = reservationRepository.countByUserIdAndStatus(userId, ReservationStatus.ACTIVE);
        if (totalReservations >= 5) {
            throw new RuntimeException("User cannot reserve more than 5 books");
        }

        // 2️⃣ Validar disponibilidad del libro
        Book book = reservation.getBook();
        Long bookId = book.getId();
        Book bookEntity = bookService.getBookById(bookId); // usa BookService
        if (bookEntity.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Book is not available");
        }

        // 3️⃣ Reducir stock del libro
        bookService.decreaseAvailableQuantity(bookEntity);

        // 4️⃣ Configurar reserva
        reservation.setBook(bookEntity);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setReservationDate(LocalDate.now());

        return reservationRepository.save(reservation);
    }

    // Obtener reservas por usuario
    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    // Eliminar/cancelar reserva
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getStatus() == ReservationStatus.ACTIVE) {
            // Restaurar stock del libro
            bookService.increaseAvailableQuantity(reservation.getBook());
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

    }

}