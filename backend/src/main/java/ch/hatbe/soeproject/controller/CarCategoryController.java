package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.persistance.entities.CarCategory;
import ch.hatbe.soeproject.service.carCategory.CarCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carcategories")
public class CarCategoryController {
    private final CarCategoryService carCategoryService;
    public CarCategoryController(CarCategoryService carCategoryService) {
        this.carCategoryService = carCategoryService;
    }

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<CarCategory>> getAllCarCategories() {
        List<CarCategory> categories = carCategoryService.getAllCarCategories();
        return ResponseEntity.ok(categories);
    }
}
