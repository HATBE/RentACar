package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.CarCategory;
import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import ch.hatbe.soeproject.persistance.entities.requests.PostCarRequest;
import ch.hatbe.soeproject.persistance.factories.CarFactory;
import ch.hatbe.soeproject.persistance.repositories.BookingRepository;
import ch.hatbe.soeproject.persistance.repositories.CarCategoryRepository;
import ch.hatbe.soeproject.persistance.repositories.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    private final CarCategoryRepository carCategoryRepository;

    public CarServiceImpl(CarRepository carRepository, BookingRepository bookingRepository, CarCategoryRepository carCategoryRepository) {
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
        this.carCategoryRepository = carCategoryRepository;
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

    public Car createCar(PostCarRequest request) throws IllegalArgumentException {
        // TODO: VALIDATE

        CarCategory carCategory = carCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Car car = CarFactory.getInstance().createCar(request);

        return carRepository.save(car);
    }

    public Map<String, Object> getCarOptions() {
        GearType[] gearTypes = GearType.values();
        FuelType[] fuelTypes = FuelType.values();


        Map<String, Object> options = new HashMap<>();
        options.put("gearTypes", gearTypes);
        options.put("fuelTypes", fuelTypes);

        return options;
    }

    @Transactional // make sure that the whole method is executed in a single transaction
    public boolean deleteCarById(int carId) {
        // check if car exists
        Optional<Car> car = carRepository.findById(carId);

        if (car.isEmpty()) {
            return false;
        }

        // delete all bookings for this car
        bookingRepository.deleteByCarId(carId);

        carRepository.deleteById(carId);

        return true;
    }
}
