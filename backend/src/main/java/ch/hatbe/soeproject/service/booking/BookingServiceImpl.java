package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getBookingsByCarId(int carId) {
        return bookingRepository.findAllByCarId(carId);
    }

    public List<Booking> getBookingsByUserId(int carId) {
        return bookingRepository.findAllByUserId(carId);
    }

    public Optional<Booking> getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public boolean deleteBookingById(int bookingId) {
        // check if booking exists
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if (booking.isEmpty()) {
            return false;
        }

        bookingRepository.deleteById(bookingId);

        return true;
    }
}
