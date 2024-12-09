package ch.hatbe.soeproject.persistance.factories;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.requests.PostCarRequest;

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

    public Car createCar(PostCarRequest request) {
        Car car = new Car();
        
        car.setMake(request.getMake());
        car.setModel(request.getModel());
        car.setBuildYear(request.getBuildYear());
        car.setHorsepower(request.getHorsePower());
        car.setSeatsCount(request.getSeatsCount());
        car.setPricePerDay(request.getPricePerDay());
        car.setGearType(request.getGearType());
        car.setFuelType(request.getFuelType());

        return car;
    }
}
