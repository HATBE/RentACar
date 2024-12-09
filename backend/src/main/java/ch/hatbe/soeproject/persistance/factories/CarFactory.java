package ch.hatbe.soeproject.persistance.factories;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.CarCategory;
import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;

public class CarFactory {
    private static CarFactory instance;

    private CarFactory() {
    }

    public static CarFactory getInstance() {
        if (instance == null) {
            instance = new CarFactory();
        }
        return instance;
    }

    public Car createCar(String make, String model, int buildYear, int horsePower, int seatsCount, float pricePerDay, GearType gearType, FuelType fuelType, CarCategory category) {
        Car car = new Car();

        car.setMake(make);
        car.setModel(model);
        car.setBuildYear(buildYear);
        car.setHorsepower(horsePower);
        car.setSeatsCount(seatsCount);
        car.setPricePerDay(pricePerDay);
        car.setGearType(gearType);
        car.setFuelType(fuelType);
        car.setCategory(category);

        return car;
    }
}
