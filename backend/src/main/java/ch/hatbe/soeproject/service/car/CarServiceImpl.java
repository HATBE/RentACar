package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import ch.hatbe.soeproject.persistance.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getCars(
            Integer buildYearFrom,
            Integer buildYearTo,
            String make,
            String category,
            Float priceMin,
            Float priceMax,
            Integer seatsMin,
            Integer seatsMax,
            GearType gearType,
            FuelType fuelType,
            String priceSort,
            String horsepowerSort,
            String buildYearSort) {

        return carRepository.findAll(buildYearFrom, buildYearTo, make, category, priceMin, priceMax, seatsMin, seatsMax, gearType, fuelType, priceSort, horsepowerSort, buildYearSort); // Updated call to repository
    }

    public Optional<Car> getCarById(int carId) {
        return carRepository.findById(carId);
    }

    public Map<String, Object> getCarOptions() {
        GearType[] gearTypes = GearType.values();
        FuelType[] fuelTypes = FuelType.values();


        Map<String, Object> options = new HashMap<>();
        options.put("gearTypes", gearTypes);
        options.put("fuelTypes", fuelTypes);

        return options;
    }
}
