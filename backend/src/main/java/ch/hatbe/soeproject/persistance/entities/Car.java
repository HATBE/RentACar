package ch.hatbe.soeproject.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "Cid")
    private int id;

    @Column(name = "Cmake", nullable = false)
    private String make;

    @Column(name = "Cmodel", nullable = false)
    private String model;

    @Column(name = "CbuildYear", nullable = false)
    private int buildYear;

    @Column(name = "Ccategory", nullable = false)
    private String category;

    @Column(name = "Chorsepower", nullable = false)
    private int horsepower;

    @Column(name = "Cseatscount", nullable = false)
    private int seatsCount;

    @Column(name = "CpricePerDay", nullable = false)
    private float pricePerDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "CgearType", nullable = false)
    private GearType gearType;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}