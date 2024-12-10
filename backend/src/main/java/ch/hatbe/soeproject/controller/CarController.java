package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.controller.response.ErrorResponse;
import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import ch.hatbe.soeproject.persistance.entities.requests.PatchCarRequest;
import ch.hatbe.soeproject.persistance.entities.requests.PostCarRequest;
import ch.hatbe.soeproject.service.car.CarService;
import ch.hatbe.soeproject.utils.RequestValidator;
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
        priceSort = RequestValidator.validateSortDirection(priceSort);
        horsepowerSort = RequestValidator.validateSortDirection(horsepowerSort);
        buildYearSort = RequestValidator.validateSortDirection(buildYearSort);

        List<Car> cars = carService.getCars(buildYearFrom, buildYearTo, make, category, priceMin, priceMax, seatsMin, seatsMax, gearType, fuelType, priceSort, horsepowerSort, buildYearSort, startDate, endDate);

        if (cars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No Cars available", "CARS_NOT_FOUND"));
        }

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<?> getCar(@PathVariable int carId) {
        Optional<Car> car = this.carService.getCarById(carId);

        if (car.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Car not found", "CAR_NOT_FOUND"));
        }

        return ResponseEntity.ok(car);
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> postCar(@RequestBody @Validated PostCarRequest request) {
        try {
            Car car = this.carService.createCar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(car);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage(), "INTERNAL_ERROR"));
        }
    }

    @PatchMapping("/{carId}")
    public ResponseEntity<?> patchCar(@PathVariable int carId, @RequestBody @Validated PatchCarRequest request) {
        try {
            Car updatedCar = carService.updateCar(carId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), "INVALID_REQUEST"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage(), "INTERNAL_ERROR"));
        }
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<?> deleteCar(@PathVariable int carId) {
        if (!this.carService.deleteCarById(carId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Car not found", "CAR_NOT_FOUND"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse("Car successfully deleted", "CAR_DELETED"));
    }

    @GetMapping("/options")
    public ResponseEntity<?> getCarOptions() {
        return ResponseEntity.ok(this.carService.getCarOptions());
    }
}
