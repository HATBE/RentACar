package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.controller.response.ErrorResponse;
import ch.hatbe.soeproject.persistance.entities.CarCategory;
import ch.hatbe.soeproject.service.carCategory.CarCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    @Operation(
            summary = "Fetch all Car Categories",
            description = "Retrieve a list of all available car categories."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car categories retrieved successfully", content = @Content(schema = @Schema(implementation = CarCategory.class))),
            @ApiResponse(responseCode = "204", description = "No car categories found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> getAllCarCategories() {
        logger.info("Fetching all car categories");

        try {
            List<CarCategory> categories = carCategoryService.getAllCarCategories();

            if (categories.isEmpty()) {
                logger.warn("No car categories found");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            logger.debug("Fetched car categories: {}", categories);
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception e) {
            logger.error("Error fetching car categories", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An error occurred while fetching car categories", "INTERNAL_SERVER_ERROR"));
        }
    }
}
