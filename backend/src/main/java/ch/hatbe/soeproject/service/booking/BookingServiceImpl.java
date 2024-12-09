package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.requests.PostBookingRequest;
import ch.hatbe.soeproject.persistance.repositories.BookingRepository;
import ch.hatbe.soeproject.persistance.repositories.CarRepository;
import ch.hatbe.soeproject.utils.BookingUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, CarRepository carRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository;
    }

    public List<Booking> getBookingsByCarId(int carId, boolean future) {
        return bookingRepository.findAllByCarId(carId, future);
    }

    public Optional<Booking> getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public Booking createBooking(PostBookingRequest request) throws IllegalArgumentException {
        // validate start end date TODO:
        if (request.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if (request.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("End date cannot be in the past");
        }

        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        if (BookingUtil.doBookingsOverlap(this.getBookingsByCarId(car.getId(), false), request.getStartDate(), request.getEndDate())) {
            throw new IllegalArgumentException("Car is already booked for the selected dates");
        }

        float carPrice = car.getPricePerDay() * (request.getEndDate().toEpochDay() - request.getStartDate().toEpochDay() + 1);
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        LocalDateTime nowTimestamp = LocalDateTime.now();

        // TODO: constructor // factory?
        Booking booking = new Booking();
        booking.setCar(car);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setCreationDate(nowTimestamp);
        booking.setCalculatedPrice(carPrice);

        return bookingRepository.save(booking);
    }

    public boolean deleteBookingById(int bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if (booking.isEmpty()) {
            return false;
        }

        bookingRepository.deleteById(bookingId);

        return true;
    }
}
