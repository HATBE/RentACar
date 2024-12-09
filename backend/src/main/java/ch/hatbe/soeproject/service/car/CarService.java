package ch.hatbe.soeproject.service.car;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import ch.hatbe.soeproject.persistance.entities.requests.PostCarRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CarService {
    List<Car> getCars(Integer buildYearFrom, Integer buildYearTo, String make, String category, Float priceMin, Float priceMax, Integer seatsMin, Integer seatsMax, GearType gearType, FuelType fuelType, String priceSort, String horsepowerSort, String buildYearSort);

    Optional<Car> getCarById(int carid);

    Map<String, Object> getCarOptions();

    boolean deleteCarById(int carId);

    Car createCar(PostCarRequest request) throws IllegalArgumentException;
}
