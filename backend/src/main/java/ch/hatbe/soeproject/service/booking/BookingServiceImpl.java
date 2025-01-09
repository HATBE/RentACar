package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.requests.PostBookingRequest;
import ch.hatbe.soeproject.persistance.factories.BookingFactory;
import ch.hatbe.soeproject.persistance.repositories.BookingRepository;
import ch.hatbe.soeproject.persistance.repositories.CarRepository;
import ch.hatbe.soeproject.utils.BookingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, CarRepository carRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository;
    }

    public List<Booking> getBookingsByCarId(int carId, boolean future) {
        logger.debug("Fetching bookings for carId: {} with future filter: {}", carId, future);
        return bookingRepository.findAllByCarId(carId, future);
    }

    public List<Booking> getBookings() {
        logger.debug("Fetching bookings ");
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(int bookingId) {
        logger.debug("Fetching booking by ID: {}", bookingId);
        return bookingRepository.findById(bookingId);
    }

    public Booking createBooking(PostBookingRequest request) throws IllegalArgumentException {
        logger.debug("Creating booking for carId: {} with startDate: {} and endDate: {}",
                request.getCarId(), request.getStartDate(), request.getEndDate());

        if (request.getStartDate().isBefore(LocalDate.now())) {
            logger.warn("Start date {} is in the past", request.getStartDate());
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if (request.getEndDate().isBefore(LocalDate.now())) {
            logger.warn("End date {} is in the past", request.getEndDate());
            throw new IllegalArgumentException("End date cannot be in the past");
        }

        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> {
                    logger.error("Car with ID {} not found", request.getCarId());
                    return new IllegalArgumentException("Car not found");
                });

        if (BookingValidator.doBookingsOverlap(this.getBookingsByCarId(car.getId(), false), request.getStartDate(), request.getEndDate())) {
            logger.warn("Overlap detected for carId: {} between {} and {}", request.getCarId(), request.getStartDate(), request.getEndDate());
            throw new IllegalArgumentException("Car is already booked for the selected dates");
        }

        Booking booking = BookingFactory.getInstance().createBooking(car, request.getStartDate(), request.getEndDate(), car.getPricePerDay(), request.getCustomerName());
        Booking savedBooking = bookingRepository.save(booking);

        logger.info("Booking created successfully with ID: {}", savedBooking.getId());
        return savedBooking;
    }

    public boolean deleteBookingById(int bookingId) {
        logger.debug("Deleting booking with ID: {}", bookingId);
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if (booking.isEmpty()) {
            logger.warn("Attempted to delete non-existent booking with ID: {}", bookingId);
            return false;
        }

        bookingRepository.deleteById(bookingId);
        logger.info("Booking with ID: {} deleted successfully", bookingId);
        return true;
    }
}
