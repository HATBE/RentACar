package ch.hatbe.soeproject.utils;

import ch.hatbe.soeproject.persistance.entities.Booking;

import java.time.LocalDate;
import java.util.List;

public class BookingUtil {
    /**
     * @param bookings a list of bookings
     * @param startDate start date of a booking
     * @param endDate end date of a booking
     * @return returns true if one of the bookings intersects with the range (startDate, endDate)
     */
    public static boolean doBookingsOverlap(List<Booking> bookings, LocalDate startDate, LocalDate endDate) {
        List<Booking> overlappingBookings = bookings.stream()
                .filter(b -> !(b.getEndDate().isBefore(startDate) || b.getStartDate().isAfter(endDate)))
                .toList();

        return !overlappingBookings.isEmpty();
    }
}
