package ch.hatbe.soeproject.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "carcategories")
public class CarCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "CCid")
    private int id;

    @Column(name = "CCname", nullable = false)
    private String name;

    @Column(name = "CCimage", nullable = false)
    private String image;
}
