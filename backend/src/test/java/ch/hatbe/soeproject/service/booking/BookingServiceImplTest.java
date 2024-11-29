package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.persistance.entities.Booking;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceImplTest {

    @Test
    void testDoBookingsOverlap_NoOverlap() {
        List<Booking> bookings = List.of(
                createBooking(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10)),
                createBooking(LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 10))
        );
        LocalDate startDate = LocalDate.of(2023, 3, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 10);

        boolean result = doBookingsOverlap(bookings, startDate, endDate);

        assertFalse(result, "Bookings should not overlap");
    }

    @Test
    void testDoBookingsOverlap_WithOverlap() {
        List<Booking> bookings = List.of(
                createBooking(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10)),
                createBooking(LocalDate.of(2023, 3, 5), LocalDate.of(2023, 3, 15))
        );
        LocalDate startDate = LocalDate.of(2023, 3, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 10);

        boolean result = doBookingsOverlap(bookings, startDate, endDate);

        assertTrue(result, "Bookings should overlap");
    }

    @Test
    void testDoBookingsOverlap_BoundaryOverlap() {
        List<Booking> bookings = List.of(
                createBooking(LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 5)),
                createBooking(LocalDate.of(2023, 3, 10), LocalDate.of(2023, 3, 15))
        );
        LocalDate startDate = LocalDate.of(2023, 3, 5);
        LocalDate endDate = LocalDate.of(2023, 3, 10);

        boolean result = doBookingsOverlap(bookings, startDate, endDate);

        assertTrue(result, "Bookings should overlap at boundaries");
    }

    @Test
    void testDoBookingsOverlap_EmptyBookings() {
        List<Booking> bookings = List.of();
        LocalDate startDate = LocalDate.of(2023, 3, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 10);

        boolean result = doBookingsOverlap(bookings, startDate, endDate);

        assertFalse(result, "No bookings should result in no overlap");
    }

    private Booking createBooking(LocalDate startDate, LocalDate endDate) {
        Booking booking = new Booking();
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        return booking;
    }

    private boolean doBookingsOverlap(List<Booking> bookings, LocalDate startDate, LocalDate endDate) {
        List<Booking> overlappingBookings = bookings.stream()
                .filter(b -> !(b.getEndDate().isBefore(startDate) || b.getStartDate().isAfter(endDate)))
                .toList();

        return !overlappingBookings.isEmpty();
    }
}