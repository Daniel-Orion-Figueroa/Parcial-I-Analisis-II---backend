package com.library.libraryapi.dto;

import com.library.libraryapi.models.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    // User mappings
    public UserDto toUserDto(User user) {
        if (user == null) return null;
        return new UserDto(user);
    }

    public List<UserDto> toUserDtoList(List<User> users) {
        return users.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    // Book mappings
    public BookDto toBookDto(Book book) {
        if (book == null) return null;
        return new BookDto(book);
    }

    public List<BookDto> toBookDtoList(List<Book> books) {
        return books.stream()
                .map(this::toBookDto)
                .collect(Collectors.toList());
    }

    // Reservation mappings
    public ReservationDto toReservationDto(Reservation reservation) {
        if (reservation == null) return null;
        return new ReservationDto(reservation);
    }

    public List<ReservationDto> toReservationDtoList(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::toReservationDto)
                .collect(Collectors.toList());
    }

    // Loan mappings
    public LoanDto toLoanDto(Loan loan) {
        if (loan == null) return null;
        return new LoanDto(loan);
    }

    public List<LoanDto> toLoanDtoList(List<Loan> loans) {
        return loans.stream()
                .map(this::toLoanDto)
                .collect(Collectors.toList());
    }
}
