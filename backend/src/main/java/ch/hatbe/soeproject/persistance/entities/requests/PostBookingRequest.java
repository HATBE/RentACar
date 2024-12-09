package ch.hatbe.soeproject.persistance.entities.requests;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostBookingRequest {
    private int carId;
    private LocalDate startDate;
    private LocalDate endDate;
}
