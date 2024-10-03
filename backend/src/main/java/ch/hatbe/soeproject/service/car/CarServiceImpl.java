package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.entities.Car;
import ch.hatbe.soeproject.persistance.Database;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarServiceImpl implements CarService {
    private final Database database;

    public CarServiceImpl(Database database) {
        this.database = database;
    }

    public ArrayList<Car> getCars() {
        return database.getCars();
    }
}
