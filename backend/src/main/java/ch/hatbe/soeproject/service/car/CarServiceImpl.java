package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.GearType;
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

    @Override
    public List<Car> getCars(Integer buildYearFrom, Integer buildYearTo, String make, String category,  Float priceMin, Float priceMax, Integer seatsMin, Integer seatsMax, GearType gearType, String priceSort, String horsepowerSort, String buildYearSort) {
        return carRepository.findAllByMultipleFilters(buildYearFrom, buildYearTo, make, category, priceMin, priceMax, seatsMin, seatsMax, gearType, priceSort, horsepowerSort, buildYearSort);
    }
    public Optional<Car> getCarById(int carId) {
        return carRepository.findById(carId);
    }
}
