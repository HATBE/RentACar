package ch.hatbe.soeproject.persistance.repositories.cars;

import ch.hatbe.soeproject.persistance.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE c.buildYear BETWEEN :startYear AND :endYear")
    List<Car> findByBuildYearRange(@Param("startYear") int startYear, @Param("endYear") int endYear);
}