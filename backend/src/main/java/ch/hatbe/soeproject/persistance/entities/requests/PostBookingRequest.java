package ch.hatbe.soeproject.persistance.entities.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
}
