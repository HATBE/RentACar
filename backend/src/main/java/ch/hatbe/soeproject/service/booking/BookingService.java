package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.persistance.entities.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> getBookingsByCarId(int carId, boolean future);

    List<Booking> getBookingsByUserId(int userId, boolean future);

    Optional<Booking> getBookingById(int bookingId);

    boolean deleteBookingById(int bookingId);
}
