package ch.hatbe.soeproject.persistance;

import ch.hatbe.soeproject.entities.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class InMemoryDatabase implements Database {
    @Override
    public ArrayList<String> getUsers() {
        ArrayList<String> users = new ArrayList<>();
        users.add("hans");
        users.add("peter");
        users.add("jürgen");

        return users;
    }

    @Override
    public ArrayList<String> getBookings() {
        ArrayList<String> cars = new ArrayList<>();
        cars.add("Suzuki");
        cars.add("BMW");
        cars.add("Nissan");

        return cars;
    }

    @Override
    public ArrayList<Car> getCars() {
        ArrayList<Car> bookings = new ArrayList<>();
        bookings.add(new Car(1, "test1"));
        bookings.add(new Car(2, "test2"));
        bookings.add(new Car(3, "test3"));

        return bookings;
    }
}
