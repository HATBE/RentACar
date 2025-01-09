package ch.hatbe.soeproject.persistance.repositories;

import ch.hatbe.soeproject.persistance.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    // Future == true | displays only bookings that are in the future (including today)
    @Query("SELECT b FROM Booking b WHERE b.car.id = :carId AND (:future = FALSE OR b.endDate >= CURRENT_DATE) ORDER BY b.startDate ASC, b.endDate ASC")
    List<Booking> findAllByCarId(@Param("carId") Integer carId, @Param("future") Boolean future);

    @Query("SELECT b FROM Booking b WHERE b.endDate >= CURRENT_DATE ORDER BY b.startDate ASC, b.endDate ASC")
    List<Booking> findAll();

    @Modifying
    @Query("DELETE FROM Booking b WHERE b.car.id = :carId")
    void deleteByCarId(Integer carId);
}