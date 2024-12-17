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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Fetch all cars", description = "Retrieve a list of all cars with optional filters and sorting.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cars retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No cars found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> getCars(
            @Parameter(description = "Filter by build year (from)") @RequestParam(value = "buildYearFrom", required = false) Integer buildYearFrom,
            @Parameter(description = "Filter by build year (to)") @RequestParam(value = "buildYearTo", required = false) Integer buildYearTo,
            @Parameter(description = "Filter by car make") @RequestParam(value = "make", required = false) String make,
            @Parameter(description = "Filter by category") @RequestParam(value = "category", required = false) String category,
            @Parameter(description = "Minimum price") @RequestParam(value = "priceMin", required = false) Float priceMin,
            @Parameter(description = "Maximum price") @RequestParam(value = "priceMax", required = false) Float priceMax,
            @Parameter(description = "Minimum seats") @RequestParam(value = "seatsMin", required = false) Integer seatsMin,
            @Parameter(description = "Maximum seats") @RequestParam(value = "seatsMax", required = false) Integer seatsMax,
            @Parameter(description = "Filter by gear type") @RequestParam(value = "gearType", required = false) GearType gearType,
            @Parameter(description = "Filter by fuel type") @RequestParam(value = "fuelType", required = false) FuelType fuelType,
            @Parameter(description = "Filter by availability start date") @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Filter by availability end date") @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "Sort by price (asc/desc)") @RequestParam(value = "priceSort", required = false) String priceSort,
            @Parameter(description = "Sort by horsepower (asc/desc)") @RequestParam(value = "horsepowerSort", required = false) String horsepowerSort,
            @Parameter(description = "Sort by build year (asc/desc)") @RequestParam(value = "buildYearSort", required = false) String buildYearSort
    ) {
        logger.info("Fetching cars with filters and sorting options");
        priceSort = RequestValidator.validateSortDirection(priceSort);
        horsepowerSort = RequestValidator.validateSortDirection(horsepowerSort);
        buildYearSort = RequestValidator.validateSortDirection(buildYearSort);

        List<Car> cars = carService.getCars(buildYearFrom, buildYearTo, make, category, priceMin, priceMax, seatsMin, seatsMax, gearType, fuelType, priceSort, horsepowerSort, buildYearSort, startDate, endDate);

        if (cars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No Cars available", "CARS_NOT_FOUND"));
        }
        return ResponseEntity.ok(cars);
    }

    @Operation(summary = "Fetch car by ID", description = "Retrieve a single car by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{carId}")
    public ResponseEntity<?> getCar(
            @Parameter(description = "ID of the car to fetch") @PathVariable int carId) {
        logger.info("Fetching car with ID: {}", carId);
        Optional<Car> car = this.carService.getCarById(carId);

        if (car.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Car not found", "CAR_NOT_FOUND"));
        }
        return ResponseEntity.ok(car);
    }

    @Operation(summary = "Create a new car", description = "Submit a new car with all required details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Car created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> postCar(
            @Parameter(description = "Car creation request") @RequestBody @Validated PostCarRequest request) {
        logger.info("Creating car with request: {}", request);
        try {
            Car car = this.carService.createCar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(car);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        }
    }

    @Operation(summary = "Update car details", description = "Update specific fields of an existing car by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Car updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{carId}")
    public ResponseEntity<?> patchCar(
            @Parameter(description = "ID of the car to update") @PathVariable int carId,
            @Parameter(description = "Fields to update") @RequestBody @Validated PatchCarRequest request) {
        logger.info("Updating car with ID: {}", carId);
        try {
            Car updatedCar = carService.updateCar(carId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        }
    }

    @Operation(summary = "Delete a car", description = "Delete a car by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{carId}")
    public ResponseEntity<?> deleteCar(
            @Parameter(description = "ID of the car to delete") @PathVariable int carId) {
        logger.info("Deleting car with ID: {}", carId);
        if (!this.carService.deleteCarById(carId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Car not found", "CAR_NOT_FOUND"));
        }
        return ResponseEntity.ok(new ErrorResponse("Car successfully deleted", "CAR_DELETED"));
    }
}
