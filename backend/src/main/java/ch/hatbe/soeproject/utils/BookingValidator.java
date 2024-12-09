package ch.hatbe.soeproject.utils;

import ch.hatbe.soeproject.persistance.entities.Booking;

import java.time.LocalDate;
import java.util.List;

public class BookingValidator {
    public static boolean doBookingsOverlap(List<Booking> bookings, LocalDate startDate, LocalDate endDate) {
        List<Booking> overlappingBookings = bookings.stream()
                .filter(b -> !(b.getEndDate().isBefore(startDate) || b.getStartDate().isAfter(endDate)))
                .toList();

        return !overlappingBookings.isEmpty();
    }
}
