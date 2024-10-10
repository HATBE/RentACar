package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.persistance.entities.Booking;

import java.util.ArrayList;
import java.util.List;

public interface BookingService {
    List<Booking> getBookings();
}
