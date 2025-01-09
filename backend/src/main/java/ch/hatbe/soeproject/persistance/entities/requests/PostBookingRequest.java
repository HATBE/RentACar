package ch.hatbe.soeproject.persistance.entities.requests;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostBookingRequest {
    @Min(value = 1, message = "Car ID must be greater than 0.")
    private Integer carId;

    @NotNull(message = "Start date cannot be null.")
    @FutureOrPresent(message = "Start date must be today or in the future.")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null.")
    @Future(message = "End date must be in the future.")
    private LocalDate endDate;

    @NotBlank(message = "Name of the customer")
    @Size(max = 50, message = "Customer name must not exceed 50 characters.")
    private String customerName;
}
