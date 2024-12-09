package ch.hatbe.soeproject.persistance.entities.requests;

import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import lombok.Getter;

@Getter
public class PostCarRequest {
    private String make;
    private String model;
    private int buildYear;
    private int horsePower;
    private int seatsCount;
    private float pricePerDay;
    private GearType gearType;
    private FuelType fuelType;
    private int categoryId;
}
