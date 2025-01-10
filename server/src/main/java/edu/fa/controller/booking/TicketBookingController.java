package edu.fa.controller;



import edu.fa.model.dto.BookingRequest;

import edu.fa.model.dto.BookingResponse;
import edu.fa.model.entities.users.User;
import edu.fa.service.TicketBookingService;

import edu.fa.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class TicketBookingController {


    private final TicketBookingService ticketBookingService;


    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/book-seats")
    public ResponseEntity<String> bookSeats(@RequestBody BookingRequest bookingRequest) {
        User currentUser = customUserDetailsService.getCurrentUser();
        ticketBookingService.bookSeats(bookingRequest.getSeatStatusIds(), currentUser);
        return ResponseEntity.ok("Бронирование успешно выполнено!");
    }

    @GetMapping("/user") public ResponseEntity<List<BookingResponse>> getUserBookings() {
        User currentUser = customUserDetailsService.getCurrentUser();
        List<BookingResponse> bookings = ticketBookingService.getUserBookings(currentUser.getId());
        return ResponseEntity.ok(bookings);
    }
}

