package ch.hatbe.soeproject.persistance.repositories;

import ch.hatbe.soeproject.persistance.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.car.id = :carId")
    List<Booking> findAllByCarId(@Param("carId") Integer carId);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    List<Booking> findAllByUserId(@Param("userId") Integer userId);
}
