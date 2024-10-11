package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars(int from, int to) {
        return carRepository.findByBuildYearRange(from, to);
    }
    public Optional<Car> getCarById(int carId) {
        return carRepository.findById(carId);
    }
}
