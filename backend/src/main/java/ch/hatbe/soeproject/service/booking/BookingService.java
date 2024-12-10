package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.entities.requests.PostBookingRequest;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> getBookingsByCarId(int carId, boolean future);

    Optional<Booking> getBookingById(int bookingId);

    boolean deleteBookingById(int bookingId);

    Booking createBooking(PostBookingRequest request) throws IllegalArgumentException;

}
