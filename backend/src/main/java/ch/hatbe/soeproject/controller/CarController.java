package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.GearType;
import ch.hatbe.soeproject.service.car.CarService;
import ch.hatbe.soeproject.validation.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    private final CarService carService;
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(value = { "", "/" })
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
            @RequestParam(value = "priceSort", required = false) String priceSort,
            @RequestParam(value = "horsepowerSort", required = false) String horsepowerSort,
            @RequestParam(value = "buildYearSort", required = false) String buildYearSort
    ) {
        priceSort = Validate.validateSortDirection(priceSort);
        horsepowerSort = Validate.validateSortDirection(horsepowerSort);
        buildYearSort = Validate.validateSortDirection(buildYearSort);

        List<Car> cars = carService.getCars(buildYearFrom, buildYearTo, make, category, priceMin, priceMax,seatsMin, seatsMax, gearType, priceSort, horsepowerSort, buildYearSort);

        if (cars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No Cars available", "CARS_NOT_FOUND"));
        }

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{carid}")
    public ResponseEntity<?> getCar(@PathVariable int carid) {
        Optional<Car> car = this.carService.getCarById(carid);

        if (car.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Car not found", "CAR_NOT_FOUND"));
        }

        return ResponseEntity.ok(car.get());
    }

    @PostMapping("/")
    public ResponseEntity<String> postCar() {
        return ResponseEntity.ok("post car");
    }

    @PatchMapping("/{carid}")
    public ResponseEntity<String> patchCar(@PathVariable int carid) {
        return ResponseEntity.ok("patch car" + carid);
    }

    @DeleteMapping("/{carid}")
    public ResponseEntity<String> deleteCar(@PathVariable int carid) {
        return ResponseEntity.ok("delete car" + carid);
    }
}
