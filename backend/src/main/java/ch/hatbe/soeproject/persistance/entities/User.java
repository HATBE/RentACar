package ch.hatbe.soeproject.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Uid")
    private int id;

    @Column(name = "Ufirstname", nullable = false)
    private String firstname;

    @Column(name = "Ulastname", nullable = false)
    private String lastname;

    @Column(name = "Uemail", nullable = false)
    private String email;

    @Column(name = "Uphone", nullable = false)
    private String phone;

    @Column(name = "UpasswordHash", nullable = false)
    private String passwordHash;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}