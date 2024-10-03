package ch.hatbe.soeproject.persistance;

import ch.hatbe.soeproject.persistance.entities.Car;

import java.util.ArrayList;

public interface Database {
    ArrayList<String> getUsers();
    ArrayList<String> getBookings();
    ArrayList<Car> getCars();
}
