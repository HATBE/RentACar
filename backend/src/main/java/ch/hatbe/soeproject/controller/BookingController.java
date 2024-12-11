package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.controller.response.ErrorResponse;
import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.entities.requests.PostBookingRequest;
import ch.hatbe.soeproject.service.booking.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBooking(@PathVariable int bookingId) {
        logger.info("Fetching booking with ID: {}", bookingId);
        Optional<Booking> booking = bookingService.getBookingById(bookingId);

        if (booking.isEmpty()) {
            logger.warn("Booking with ID: {} not found", bookingId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No Booking found", "BOOKING_NOT_FOUND"));
        }

        logger.debug("Booking found: {}", booking);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<?> getCarBookings(
            @PathVariable int carId,
            @RequestParam(value = "future", required = false, defaultValue = "false") Boolean future) {
        logger.info("Fetching bookings for car ID: {} with future flag: {}", carId, future);
        List<Booking> bookings = bookingService.getBookingsByCarId(carId, future);

        if (bookings.isEmpty()) {
            logger.warn("No bookings found for car ID: {}", carId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ErrorResponse("No Bookings found", "BOOKINGS_NOT_FOUND"));
        }

        logger.debug("Bookings found for car ID {}: {}", carId, bookings);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> postBooking(@RequestBody @Validated PostBookingRequest request) {
        logger.info("Creating booking with request: {}", request);
        try {
            Booking booking = bookingService.createBooking(request);
            logger.info("Booking created successfully: {}", booking);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating booking: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable int bookingId) {
        logger.info("Deleting booking with ID: {}", bookingId);
        if (!bookingService.deleteBookingById(bookingId)) {
            logger.warn("Booking with ID: {} not found for deletion", bookingId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Booking not found", "BOOKING_NOT_FOUND"));
        }

        logger.info("Booking with ID: {} deleted successfully", bookingId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ErrorResponse("Booking successfully deleted", "BOOKING_DELETED"));
    }
}