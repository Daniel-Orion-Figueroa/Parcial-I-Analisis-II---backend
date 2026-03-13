package com.library.libraryapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private String reservationDate;
    private String status;
    private UserDto user;
    private BookDto book;
    
    // Constructor desde entidad
    public ReservationDto(com.library.libraryapi.models.Reservation reservation) {
        this.id = reservation.getId();
        this.reservationDate = reservation.getReservationDate() != null ? reservation.getReservationDate().toString() : null;
        this.status = reservation.getStatus() != null ? reservation.getStatus().toString() : null;
        
        // Convertir entidades anidadas a DTOs para evitar referencias circulares
        if (reservation.getUser() != null) {
            this.user = new UserDto(reservation.getUser());
        }
        
        if (reservation.getBook() != null) {
            this.book = new BookDto(reservation.getBook());
        }
    }
}
