package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.CarCategory;
import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import ch.hatbe.soeproject.persistance.entities.requests.PatchCarRequest;
import ch.hatbe.soeproject.persistance.entities.requests.PostCarRequest;
import ch.hatbe.soeproject.persistance.factories.CarFactory;
import ch.hatbe.soeproject.persistance.repositories.BookingRepository;
import ch.hatbe.soeproject.persistance.repositories.CarCategoryRepository;
import ch.hatbe.soeproject.persistance.repositories.CarRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    private final CarCategoryRepository carCategoryRepository;

    public CarServiceImpl(CarRepository carRepository, BookingRepository bookingRepository, CarCategoryRepository carCategoryRepository) {
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
        this.carCategoryRepository = carCategoryRepository;
    }

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
            String buildYearSort,
            LocalDate startDate,
            LocalDate endDate
    ) {
        logger.info("Fetching cars with filters: buildYearFrom={}, buildYearTo={}, make={}, category={}, priceMin={}, priceMax={}",
                buildYearFrom, buildYearTo, make, category, priceMin, priceMax);

        return carRepository.findAll(buildYearFrom, buildYearTo, make, category, priceMin, priceMax, seatsMin, seatsMax, gearType, fuelType, startDate, endDate, priceSort, horsepowerSort, buildYearSort);
    }

    public Optional<Car> getCarById(int carId) {
        logger.info("Fetching car by ID: {}", carId);
        return carRepository.findById(carId);
    }

    public Car createCar(PostCarRequest request) throws IllegalArgumentException {
        logger.info("Creating car with make={}, model={}, buildYear={}", request.getMake(), request.getModel(), request.getBuildYear());

        CarCategory carCategory = carCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> {
                    logger.error("Category with ID {} not found", request.getCategoryId());
                    return new IllegalArgumentException("Category not found");
                });

        Car car = CarFactory.getInstance().createCar(
                request.getMake(),
                request.getModel(),
                request.getBuildYear(),
                request.getHorsePower(),
                request.getSeatsCount(),
                request.getPricePerDay(),
                request.getGearType(),
                request.getFuelType(),
                carCategory
        );

        Car savedCar = carRepository.save(car);
        logger.info("Car created successfully with ID: {}", savedCar.getId());
        return savedCar;
    }

    public Car updateCar(int carId, PatchCarRequest request) {
        logger.info("Updating car with ID: {}", carId);

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> {
                    logger.error("Car with ID {} not found", carId);
                    return new IllegalArgumentException("Car not found");
                });

        if (request.getMake() != null) car.setMake(request.getMake());
        if (request.getModel() != null) car.setModel(request.getModel());
        if (request.getBuildYear() != null) car.setBuildYear(request.getBuildYear());
        if (request.getHorsePower() != null) car.setHorsepower(request.getHorsePower());
        if (request.getSeatsCount() != null) car.setSeatsCount(request.getSeatsCount());
        if (request.getPricePerDay() != null) car.setPricePerDay(request.getPricePerDay());
        if (request.getGearType() != null) car.setGearType(request.getGearType());
        if (request.getFuelType() != null) car.setFuelType(request.getFuelType());
        if (request.getCategoryId() != null) {
            CarCategory category = carCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> {
                        logger.error("Category with ID {} not found", request.getCategoryId());
                        return new IllegalArgumentException("Category not found");
                    });
            car.setCategory(category);
        }

        Car updatedCar = carRepository.save(car);
        logger.info("Car with ID {} updated successfully", carId);
        return updatedCar;
    }

    public Map<String, Object> getCarOptions() {
        logger.debug("Fetching car options (Gear Types and Fuel Types)");
        GearType[] gearTypes = GearType.values();
        FuelType[] fuelTypes = FuelType.values();

        Map<String, Object> options = new HashMap<>();
        options.put("gearTypes", gearTypes);
        options.put("fuelTypes", fuelTypes);

        return options;
    }

    @Transactional
    public boolean deleteCarById(int carId) {
        logger.info("Deleting car with ID: {}", carId);

        Optional<Car> car = carRepository.findById(carId);
        if (car.isEmpty()) {
            logger.warn("Attempted to delete non-existent car with ID: {}", carId);
            return false;
        }

        bookingRepository.deleteByCarId(carId);
        carRepository.deleteById(carId);
        logger.info("Car with ID {} and its bookings deleted successfully", carId);
        return true;
    }
}