package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.controller.response.ErrorResponse;
import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.service.booking.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBooking(@PathVariable int bookingId) {
        Optional<Booking> booking = bookingService.getBookingById(bookingId);

        if (booking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No Booking found", "BOOKING_NOT_FOUND"));
        }

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<?> getCarBookings(@PathVariable int carId, @RequestParam(value = "future", required = false, defaultValue = "false") Boolean future) {
        List<Booking> bookings = bookingService.getBookingsByCarId(carId, future);

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No Bookings found", "BOOKINGS_NOT_FOUND"));
        }

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable int userId, @RequestParam(value = "future", required = false, defaultValue = "false") Boolean future) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId, future);

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No Bookings found", "BOOKINGS_NOT_FOUND"));
        }

        return ResponseEntity.ok(bookings);
    }

    // TODO:
    @PostMapping("/")
    public ResponseEntity<String> postBooking() {
        return ResponseEntity.ok("post booking");
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteUser(@PathVariable int bookingId) {
        if (!bookingService.deleteBookingById(bookingId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Booking not found", "BOOKING_NOT_FOUND"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse("Booking successfully deleted", "BOOKING_DELETED"));
    }
}
