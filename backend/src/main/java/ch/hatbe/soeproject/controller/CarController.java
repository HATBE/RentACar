package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.entities.Car;
import ch.hatbe.soeproject.service.car.CarService;
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
    public ResponseEntity<List<Car>> getCars() {
        return ResponseEntity.ok(this.carService.getCars());
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
