package ch.hatbe.soeproject.persistance.factories;

import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.utils.CarPriceCalculator;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingFactory {
    private static BookingFactory instance;

    private BookingFactory() {
    }

    public static BookingFactory getInstance() {
        if (instance == null) {
            instance = new BookingFactory();
        }
        return instance;
    }


    public Booking createBooking(Car car, LocalDate startDate, LocalDate endDate, float pricePerDay) {
        LocalDateTime nowTimestamp = LocalDateTime.now();

        Booking booking = new Booking();

        booking.setCar(car);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setCreationDate(nowTimestamp);
        booking.setCalculatedPrice(CarPriceCalculator.calculateCarPricePerDay(pricePerDay, startDate, endDate));

        return booking;
    }
}
