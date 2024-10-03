package ch.hatbe.soeproject.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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

    @Column(name = "CbuildYear", nullable = false) // Map to correct column name
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
    @Column(name = "CgearType", nullable = false) // Map to correct column name
    private GearType gearType;
}
