package ch.hatbe.soeproject.utils;

import ch.hatbe.soeproject.persistance.entities.Booking;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingValidatorTest {

    @Test
    void testDoBookingsOverlapNoOverlap() {
        Booking booking1 = new Booking();
        booking1.setStartDate(LocalDate.of(2023, 11, 1));
        booking1.setEndDate(LocalDate.of(2023, 11, 5));

        Booking booking2 = new Booking();
        booking2.setStartDate(LocalDate.of(2023, 11, 10));
        booking2.setEndDate(LocalDate.of(2023, 11, 15));

        List<Booking> bookings = List.of(booking1, booking2);

        LocalDate startDate = LocalDate.of(2023, 11, 6);
        LocalDate endDate = LocalDate.of(2023, 11, 9);

        boolean result = BookingValidator.doBookingsOverlap(bookings, startDate, endDate);

        assertFalse(result, "Expected no overlap between the bookings and the date range.");
    }

    @Test
    void testDoBookingsOverlapOverlap() {
        Booking booking1 = new Booking();
        booking1.setStartDate(LocalDate.of(2023, 11, 1));
        booking1.setEndDate(LocalDate.of(2023, 11, 5));

        Booking booking2 = new Booking();
        booking2.setStartDate(LocalDate.of(2023, 11, 10));
        booking2.setEndDate(LocalDate.of(2023, 11, 15));

        List<Booking> bookings = List.of(booking1, booking2);

        LocalDate startDate = LocalDate.of(2023, 11, 3);
        LocalDate endDate = LocalDate.of(2023, 11, 7);

        boolean result = BookingValidator.doBookingsOverlap(bookings, startDate, endDate);

        assertTrue(result, "Expected an overlap between the bookings and the date range.");
    }

    @Test
    void testDoBookingsOverlapEmptyList() {
        List<Booking> bookings = List.of();

        LocalDate startDate = LocalDate.of(2023, 11, 3);
        LocalDate endDate = LocalDate.of(2023, 11, 7);

        boolean result = BookingValidator.doBookingsOverlap(bookings, startDate, endDate);

        assertFalse(result, "Expected no overlap as the bookings list is empty.");
    }

    @Test
    void testDoBookingsOverlapExactMatch() {
        Booking booking1 = new Booking();
        booking1.setStartDate(LocalDate.of(2023, 11, 1));
        booking1.setEndDate(LocalDate.of(2023, 11, 5));

        List<Booking> bookings = List.of(booking1);

        LocalDate startDate = LocalDate.of(2023, 11, 1);
        LocalDate endDate = LocalDate.of(2023, 11, 5);

        boolean result = BookingValidator.doBookingsOverlap(bookings, startDate, endDate);

        assertTrue(result, "Expected an overlap as the date range exactly matches a booking.");
    }

}