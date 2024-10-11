package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.persistance.entities.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getCars(int from, int to);
    Optional<Car> getCarById(int carid);
}
