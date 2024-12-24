package ch.hatbe.soeproject.persistance.entities.requests;

import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class PostCarRequest {
    @NotBlank(message = "Make cannot be empty or null.")
    @Size(max = 50, message = "Make must not exceed 50 characters.")
    private String make;

    @NotBlank(message = "Model cannot be empty or null.")
    @Size(max = 50, message = "Model must not exceed 50 characters.")
    private String model;

    @Min(value = 1886, message = "Build year must be at least 1886.") // year of the first car
    @Max(value = 3000, message = "Build year must be at most 3000.")
    private Integer buildYear;

    @Min(value = 1, message = "Horsepower must be at least 1.")
    @Max(value = 10000, message = "Horsepower must not exceed 10000.")
    private Integer horsePower;

    @Min(value = 1, message = "Seats count must be at least 1.")
    @Max(value = 150, message = "Seats count must not exceed 50.")
    private Integer seatsCount;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price per day must be greater than 0.")
    @DecimalMax(value = "10000.0", message = "Price per day must not exceed 10,000.")
    private float pricePerDay;

    @NotNull(message = "Gear type must be provided.")
    private GearType gearType;

    @NotNull(message = "Fuel type must be provided.")
    private FuelType fuelType;

    @Min(value = 1, message = "Category ID must be greater than 0.")
    @Max(value = 9999999, message = "Category ID must not exceed 1000.")
    private Integer categoryId;
}
