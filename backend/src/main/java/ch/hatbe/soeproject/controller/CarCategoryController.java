package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.persistance.entities.CarCategory;
import ch.hatbe.soeproject.service.carCategory.CarCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carcategories")
public class CarCategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CarCategoryController.class);
    private final CarCategoryService carCategoryService;

    public CarCategoryController(CarCategoryService carCategoryService) {
        this.carCategoryService = carCategoryService;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<CarCategory>> getAllCarCategories() {
        logger.info("Fetching all car categories");
        List<CarCategory> categories = carCategoryService.getAllCarCategories();

        if (categories.isEmpty()) {
            logger.warn("No car categories found");
        } else {
            logger.debug("Fetched car categories: {}", categories);
        }

        return ResponseEntity.ok(categories);
    }
}
