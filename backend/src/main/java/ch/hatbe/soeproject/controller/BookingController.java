package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.controller.response.ErrorResponse;
import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.entities.requests.PostBookingRequest;
import ch.hatbe.soeproject.service.booking.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get Booking by ID", description = "Fetch a booking using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBooking(
            @Parameter(description = "ID of the booking to retrieve", required = true) @PathVariable int bookingId) {
        logger.info("Fetching booking with ID: {}", bookingId);
        Optional<Booking> booking = bookingService.getBookingById(bookingId);

        if (booking.isEmpty()) {
            logger.warn("Booking with ID: {} not found", bookingId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No Booking found", "BOOKING_NOT_FOUND"));
        }

        return ResponseEntity.ok(booking);
    }

    @Operation(summary = "Get Bookings by Car ID", description = "Fetch all bookings for a given car. Optionally, filter for future bookings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No bookings found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/car/{carId}")
    public ResponseEntity<?> getCarBookings(
            @Parameter(description = "ID of the car", required = true) @PathVariable int carId,
            @Parameter(description = "Flag to filter for future bookings (default: false)")
            @RequestParam(value = "future", required = false, defaultValue = "false") Boolean future) {
        logger.info("Fetching bookings for car ID: {} with future flag: {}", carId, future);
        List<Booking> bookings = bookingService.getBookingsByCarId(carId, future);

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ErrorResponse("No Bookings found", "BOOKINGS_NOT_FOUND"));
        }

        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Create a new Booking", description = "Submit a new booking request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully",
                    content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> postBooking(
            @Parameter(description = "Booking request details", required = true)
            @RequestBody @Validated PostBookingRequest request) {
        logger.info("Creating booking with request: {}", request);
        try {
            Booking booking = bookingService.createBooking(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        }
    }

    @Operation(summary = "Delete a Booking by ID", description = "Delete an existing booking using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking deleted successfully",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(
            @Parameter(description = "ID of the booking to delete", required = true) @PathVariable int bookingId) {
        logger.info("Deleting booking with ID: {}", bookingId);
        if (!bookingService.deleteBookingById(bookingId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Booking not found", "BOOKING_NOT_FOUND"));
        }

        return ResponseEntity.ok(new ErrorResponse("Booking successfully deleted", "BOOKING_DELETED"));
    }
}
