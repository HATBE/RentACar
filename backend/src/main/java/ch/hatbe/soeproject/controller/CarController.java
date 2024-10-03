package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.entities.Car;
import ch.hatbe.soeproject.service.car.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    private final CarService carService;
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(value = { "", "/" })
    public ResponseEntity<?> getCars() {
        List<Car> cars = this.carService.getCars();

        if (cars.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No Cars available", "CAR_NOT_FOUND"));
        }

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{carid}")
    public ResponseEntity<String> getCar(@PathVariable int carid) {
        return ResponseEntity.ok("car: " + carid);
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
