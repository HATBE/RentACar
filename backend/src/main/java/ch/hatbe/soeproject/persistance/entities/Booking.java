package ch.hatbe.soeproject.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "Bid")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Uid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cid", nullable = false)
    private Car car;

    @Column(name = "BstartDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "BendDate", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "BcreationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "BcalculatedPrice", nullable = false)
    private float calculatedPrice;
}