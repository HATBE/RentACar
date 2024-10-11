package ch.hatbe.soeproject.persistance.repositories;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.GearType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c FROM Car c " +
            "WHERE (:buildYearFrom IS NULL OR c.buildYear >= :buildYearFrom) " +
            "AND (:buildYearTo IS NULL OR c.buildYear <= :buildYearTo) " +
            "AND (:make IS NULL OR c.make = :make) " +
            "AND (:category IS NULL OR c.category = :category) " +
            "AND (:priceMin IS NULL OR c.pricePerDay >= :priceMin) " +
            "AND (:priceMax IS NULL OR c.pricePerDay <= :priceMax) " +
            "AND (:seatsMin IS NULL OR c.seatsCount >= :seatsMin) " +
            "AND (:seatsMax IS NULL OR c.seatsCount <= :seatsMax) " +
            "AND (:gearType IS NULL OR c.gearType = :gearType) " +
            "ORDER BY " +
            "CASE WHEN :priceSort = 'ASC' THEN c.pricePerDay END ASC, " +
            "CASE WHEN :priceSort = 'DESC' THEN c.pricePerDay END DESC, " +
            "CASE WHEN :horsepowerSort = 'ASC' THEN c.horsepower END ASC, " +
            "CASE WHEN :horsepowerSort = 'DESC' THEN c.horsepower END DESC, " +
            "CASE WHEN :buildYearSort = 'ASC' THEN c.buildYear END ASC, " +
            "CASE WHEN :buildYearSort = 'DESC' THEN c.buildYear END DESC")
    List<Car> findAllByMultipleFilters(
            @Param("buildYearFrom") Integer buildYearFrom,
            @Param("buildYearTo") Integer buildYearTo,
            @Param("make") String make,
            @Param("category") String category,
            @Param("priceMin") Float priceMin,
            @Param("priceMax") Float priceMax,
            @Param("seatsMin") Integer seatsMin,
            @Param("seatsMax") Integer seatsMax,
            @Param("gearType") GearType gearType,
            @Param("priceSort") String priceSort,
            @Param("horsepowerSort") String horsepowerSort,
            @Param("buildYearSort") String buildYearSort
    );
}