package com.library.libraryapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDto {
    private Long bookId;
    private Long userId;
    private String reservationDate;
    private String status;
}
