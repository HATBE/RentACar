package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.controller.response.ErrorResponse;
import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import ch.hatbe.soeproject.persistance.entities.requests.PatchCarRequest;
import ch.hatbe.soeproject.persistance.entities.requests.PostCarRequest;
import ch.hatbe.soeproject.service.car.CarService;
import ch.hatbe.soeproject.utils.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> getCars(
            @RequestParam(value = "buildYearFrom", required = false) Integer buildYearFrom,
            @RequestParam(value = "buildYearTo", required = false) Integer buildYearTo,
            @RequestParam(value = "make", required = false) String make,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "priceMin", required = false) Float priceMin,
            @RequestParam(value = "priceMax", required = false) Float priceMax,
            @RequestParam(value = "seatsMin", required = false) Integer seatsMin,
            @RequestParam(value = "seatsMax", required = false) Integer seatsMax,
            @RequestParam(value = "gearType", required = false) GearType gearType,
            @RequestParam(value = "fuelType", required = false) FuelType fuelType,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "priceSort", required = false) String priceSort,
            @RequestParam(value = "horsepowerSort", required = false) String horsepowerSort,
            @RequestParam(value = "buildYearSort", required = false) String buildYearSort
    ) {
        logger.info("Fetching cars with filters - buildYearFrom: {}, buildYearTo: {}, make: {}, category: {}, price range: {} to {}, seats range: {} to {}, gearType: {}, fuelType: {}, startDate: {}, endDate: {}, sort options: [price: {}, horsepower: {}, buildYear: {}]",
                buildYearFrom, buildYearTo, make, category, priceMin, priceMax, seatsMin, seatsMax, gearType, fuelType, startDate, endDate, priceSort, horsepowerSort, buildYearSort);

        priceSort = RequestValidator.validateSortDirection(priceSort);
        horsepowerSort = RequestValidator.validateSortDirection(horsepowerSort);
        buildYearSort = RequestValidator.validateSortDirection(buildYearSort);

        List<Car> cars = carService.getCars(buildYearFrom, buildYearTo, make, category, priceMin, priceMax, seatsMin, seatsMax, gearType, fuelType, priceSort, horsepowerSort, buildYearSort, startDate, endDate);

        if (cars.isEmpty()) {
            logger.warn("No cars found with the given filters");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No Cars available", "CARS_NOT_FOUND"));
        }

        logger.debug("Fetched cars: {}", cars);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<?> getCar(@PathVariable int carId) {
        logger.info("Fetching car with ID: {}", carId);
        Optional<Car> car = this.carService.getCarById(carId);

        if (car.isEmpty()) {
            logger.warn("Car with ID: {} not found", carId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Car not found", "CAR_NOT_FOUND"));
        }

        logger.debug("Fetched car: {}", car);
        return ResponseEntity.ok(car);
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> postCar(@RequestBody @Validated PostCarRequest request) {
        logger.info("Creating car with request: {}", request);
        try {
            Car car = this.carService.createCar(request);
            logger.info("Car created successfully: {}", car);
            return ResponseEntity.status(HttpStatus.CREATED).body(car);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating car: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        } catch (Exception e) {
            logger.error("Unexpected error while creating car: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage(), "INTERNAL_ERROR"));
        }
    }

    @PatchMapping("/{carId}")
    public ResponseEntity<?> patchCar(@PathVariable int carId, @RequestBody @Validated PatchCarRequest request) {
        logger.info("Updating car with ID: {} using request: {}", carId, request);
        try {
            Car updatedCar = carService.updateCar(carId, request);
            logger.info("Car updated successfully: {}", updatedCar);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCar);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating car: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        } catch (Exception e) {
            logger.error("Unexpected error while updating car: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage(), "INTERNAL_ERROR"));
        }
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<?> deleteCar(@PathVariable int carId) {
        logger.info("Deleting car with ID: {}", carId);
        if (!this.carService.deleteCarById(carId)) {
            logger.warn("Car with ID: {} not found for deletion", carId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Car not found", "CAR_NOT_FOUND"));
        }

        logger.info("Car with ID: {} deleted successfully", carId);
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse("Car successfully deleted", "CAR_DELETED"));
    }

    @GetMapping("/options")
    public ResponseEntity<?> getCarOptions() {
        logger.info("Fetching car options");
        return ResponseEntity.ok(this.carService.getCarOptions());
    }
}
