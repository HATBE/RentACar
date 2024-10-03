package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.service.booking.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(value = { "", "/" })
    public ResponseEntity<String> getBookings() {
        return ResponseEntity.ok("bookings");
    }

    @GetMapping("/cars/{carid}")
    public ResponseEntity<String> getCarBookings(@PathVariable int carid) {
        return ResponseEntity.ok("car bookings: " + carid);
    }

    @GetMapping("/users/{userid}")
    public ResponseEntity<String> getUserBookings(@PathVariable int userid) {
        return ResponseEntity.ok("user bookings: " + userid);
    }

    @GetMapping("/{bookingid}")
    public ResponseEntity<String> getBooking(@PathVariable int bookingid) {
        return ResponseEntity.ok("booking: " + bookingid);
    }

    @PostMapping("/")
    public ResponseEntity<String> postBooking() {
        return ResponseEntity.ok("post booking");
    }

    @DeleteMapping("/{bookingid}")
    public ResponseEntity<String> deleteUser(@PathVariable int bookingid) {
        return ResponseEntity.ok("delete booking" + bookingid);
    }
}
