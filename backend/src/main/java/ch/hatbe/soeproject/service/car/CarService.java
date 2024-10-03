package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.persistance.entities.Car;

import java.util.List;

public interface CarService {
    List<Car> getCars(int from, int to);
}
