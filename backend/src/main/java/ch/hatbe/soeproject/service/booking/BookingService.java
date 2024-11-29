package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.entities.requests.CreateBookingRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> getBookingsByCarId(int carId, boolean future);

    List<Booking> getBookingsByUserId(int userId, boolean future);

    Optional<Booking> getBookingById(int bookingId);

    boolean deleteBookingById(int bookingId);

    boolean doBookingsOverlap(List<Booking> bookings, LocalDate startDate, LocalDate endDate);

    Booking createBooking(CreateBookingRequest request) throws IllegalArgumentException;
}
