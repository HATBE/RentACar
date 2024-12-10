package ch.hatbe.soeproject.persistance.entities.requests;

import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchCarRequest {
    private String make;
    private String model;
    private Integer buildYear;
    private Integer horsePower;
    private Integer seatsCount;
    private Float pricePerDay;
    private GearType gearType;
    private FuelType fuelType;
    private Integer categoryId;
}