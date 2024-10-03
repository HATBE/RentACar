package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.entities.Car;
import ch.hatbe.soeproject.persistance.Database;
import ch.hatbe.soeproject.persistance.cars.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findByMakeAndBuildYear("Nissan", 1998);
    }
}
