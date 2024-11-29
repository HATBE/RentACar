package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.User;
import ch.hatbe.soeproject.persistance.entities.requests.CreateBookingRequest;
import ch.hatbe.soeproject.persistance.repositories.BookingRepository;
import ch.hatbe.soeproject.persistance.repositories.CarRepository;
import ch.hatbe.soeproject.persistance.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, CarRepository carRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public List<Booking> getBookingsByCarId(int carId, boolean future) {
        return bookingRepository.findAllByCarId(carId, future);
    }

    public List<Booking> getBookingsByUserId(int carId, boolean future) {
        return bookingRepository.findAllByUserId(carId, future);
    }

    public Optional<Booking> getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId);
    }

    private boolean doBookingsOverlap(List<Booking> bookings, LocalDate startDate, LocalDate endDate) {
        List<Booking> overlappingBookings = bookings.stream()
                .filter(b -> !(b.getEndDate().isBefore(startDate) || b.getStartDate().isAfter(endDate)))
                .toList();

        return !overlappingBookings.isEmpty();
    }

    public Booking createBooking(CreateBookingRequest request) throws IllegalArgumentException {
        // Check if startDate or endDate is in the past
        if (request.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if (request.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("End date cannot be in the past");
        }

        // Validate user existence
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Validate car existence
        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        // Check for overlapping bookings
        if (this.doBookingsOverlap(this.getBookingsByCarId(car.getId(), false), request.getStartDate(), request.getEndDate())) {
            throw new IllegalArgumentException("Car is already booked for the selected dates");
        }

        // Create and save booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCar(car);
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setCreationDate(LocalDateTime.now());
        booking.setCalculatedPrice(
                car.getPricePerDay() * (ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1)
        );

        return bookingRepository.save(booking);
    }

    public boolean deleteBookingById(int bookingId) {
        // check if booking exists first
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if (booking.isEmpty()) {
            return false;
        }

        bookingRepository.deleteById(bookingId);

        return true;
    }
}
