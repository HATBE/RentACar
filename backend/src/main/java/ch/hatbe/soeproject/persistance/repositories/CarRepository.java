package ch.hatbe.soeproject.persistance.repositories;

import ch.hatbe.soeproject.persistance.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c FROM Car c WHERE c.buildYear BETWEEN :buildYearFrom AND :buildYearTo")
    List<Car> findByBuildYearRange(@Param("buildYearFrom") int buildYearFrom, @Param("buildYearTo") int buildYearTo);
}