package ch.hatbe.soeproject.persistance.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Bid")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "Uid", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "Cid", nullable = false)
    private Car car;

    @Column(name = "BstartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "BendDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "BcreationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "BcalculatedPrice", nullable = false)
    private float calculatedPrice;
}