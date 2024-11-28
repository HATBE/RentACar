package ch.hatbe.soeproject.persistance.entities.requests;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateBookingRequest {
    private int userId;
    private int carId;
    private LocalDate startDate;
    private LocalDate endDate;
}
