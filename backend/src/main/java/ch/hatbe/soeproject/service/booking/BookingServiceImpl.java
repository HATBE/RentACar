package ch.hatbe.soeproject.service.booking;

import ch.hatbe.soeproject.entities.Car;
import ch.hatbe.soeproject.persistance.Database;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookingServiceImpl implements BookingService {
    private final Database database;

    public BookingServiceImpl(Database database) {
        this.database = database;
    }

    public ArrayList<String> getBookings() {
        return database.getBookings();
    }
}
