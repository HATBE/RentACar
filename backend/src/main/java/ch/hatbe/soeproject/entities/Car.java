package ch.hatbe.soeproject.entities;

public class Car {
    private int id;
    private String make;

    public Car(int id, String make) {
        this.id = id;
        this.make = make;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }
}
