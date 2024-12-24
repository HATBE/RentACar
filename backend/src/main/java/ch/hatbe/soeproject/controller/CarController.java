package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.controller.response.ErrorResponse;
import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import ch.hatbe.soeproject.persistance.entities.requests.PatchCarRequest;
import ch.hatbe.soeproject.persistance.entities.requests.PostCarRequest;
import ch.hatbe.soeproject.service.car.CarService;
import ch.hatbe.soeproject.utils.RequestValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Fetch all cars", description = "Retrieve a list of all cars with optional filters and sorting.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cars retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No cars found")
    })
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
        logger.debug("Fetching cars with filters and sorting options.");

        priceSort = RequestValidator.validateSortDirection(priceSort);
        horsepowerSort = RequestValidator.validateSortDirection(horsepowerSort);
        buildYearSort = RequestValidator.validateSortDirection(buildYearSort);

        List<Car> cars = carService.getCars(
                buildYearFrom, buildYearTo, make, category, priceMin, priceMax, seatsMin, seatsMax,
                gearType, fuelType, priceSort, horsepowerSort, buildYearSort, startDate, endDate);

        if (cars.isEmpty()) {
            logger.warn("No cars found with the given filters.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No Cars available", "CARS_NOT_FOUND"));
        }

        logger.debug("Fetched cars: {}", cars);
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    @Operation(summary = "Fetch car by ID", description = "Retrieve a single car by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @GetMapping("/{carId}")
    public ResponseEntity<?> getCar(@PathVariable @Positive(message = "Car ID must be positive.") int carId) {
        logger.debug("Fetching car with ID: {}", carId);

        Optional<Car> car = carService.getCarById(carId);

        if (car.isEmpty()) {
            logger.warn("Car with ID: {} not found.", carId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Car not found", "CAR_NOT_FOUND"));
        }

        logger.debug("Fetched car: {}", car);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @Operation(summary = "Create a new car", description = "Submit a new car with all required details.")
    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> postCar(@RequestBody @Valid PostCarRequest request) {
        logger.debug("Creating a new car.");

        try {
            Car car = carService.createCar(request);
            logger.debug("Successfully created car: {}", car);
            return ResponseEntity.status(HttpStatus.CREATED).body(car);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input while creating car: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        }
    }

    @Operation(summary = "Update car details", description = "Update specific fields of an existing car by ID.")
    @PatchMapping("/{carId}")
    public ResponseEntity<?> patchCar(@PathVariable @Positive(message = "Car ID must be positive.") int carId, @RequestBody @Valid PatchCarRequest request) {
        logger.info("Updating car with ID: {}", carId);

        try {
            Car updatedCar = carService.updateCar(carId, request);
            logger.debug("Car updated successfully: {}", updatedCar);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCar);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating car: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        }
    }

    @Operation(summary = "Delete a car", description = "Delete a car by its ID.")
    @DeleteMapping("/{carId}")
    public ResponseEntity<?> deleteCar(@PathVariable @Positive(message = "Car ID must be positive.") int carId) {
        logger.info("Deleting car with ID: {}", carId);

        if (!carService.deleteCarById(carId)) {
            logger.warn("Car with ID: {} not found.", carId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Car not found", "CAR_NOT_FOUND"));
        }

        logger.debug("Car with ID: {} deleted successfully.", carId);
        return ResponseEntity.status(HttpStatus.OK).body("Car successfully deleted.");
    }

    @Operation(summary = "Fetch all car options", description = "Retrieve a list of all available car options.")
    @GetMapping("/options")
    public ResponseEntity<?> getCarOptions() {
        logger.info("Fetching car options");
        return ResponseEntity.status(HttpStatus.OK).body(this.carService.getCarOptions());
    }
}
