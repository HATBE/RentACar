package ch.hatbe.soeproject.persistance.repositories;

import ch.hatbe.soeproject.persistance.entities.Car;
import ch.hatbe.soeproject.persistance.entities.FuelType;
import ch.hatbe.soeproject.persistance.entities.GearType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c FROM Car c " +
            "WHERE (:buildYearFrom IS NULL OR c.buildYear >= :buildYearFrom) " +
            "AND (:buildYearTo IS NULL OR c.buildYear <= :buildYearTo) " +
            "AND (:make IS NULL OR c.make = :make) " +
            "AND (:categoryName IS NULL OR c.category.name = :categoryName) " +
            "AND (:priceMin IS NULL OR c.pricePerDay >= :priceMin) " +
            "AND (:priceMax IS NULL OR c.pricePerDay <= :priceMax) " +
            "AND (:seatsMin IS NULL OR c.seatsCount >= :seatsMin) " +
            "AND (:seatsMax IS NULL OR c.seatsCount <= :seatsMax) " +
            "AND (:gearType IS NULL OR c.gearType = :gearType) " +
            "AND (:fuelType IS NULL OR c.fuelType = :fuelType) " +
            "AND (:startDate IS NULL OR :endDate IS NULL OR NOT EXISTS (" +
            "    SELECT b FROM Booking b " +
            "    WHERE b.car.id = c.id " +
            "    AND (b.startDate <= :endDate AND b.endDate >= :startDate)" +
            ")) " +
            "ORDER BY " +
            "CASE WHEN :priceSort = 'ASC' THEN c.pricePerDay END ASC, " +
            "CASE WHEN :priceSort = 'DESC' THEN c.pricePerDay END DESC, " +
            "CASE WHEN :horsepowerSort = 'ASC' THEN c.horsepower END ASC, " +
            "CASE WHEN :horsepowerSort = 'DESC' THEN c.horsepower END DESC, " +
            "CASE WHEN :buildYearSort = 'ASC' THEN c.buildYear END ASC, " +
            "CASE WHEN :buildYearSort = 'DESC' THEN c.buildYear END DESC, " +
            "c.id ASC")
    List<Car> findAll(
            @Param("buildYearFrom") Integer buildYearFrom,
            @Param("buildYearTo") Integer buildYearTo,
            @Param("make") String make,
            @Param("categoryName") String categoryName,
            @Param("priceMin") Float priceMin,
            @Param("priceMax") Float priceMax,
            @Param("seatsMin") Integer seatsMin,
            @Param("seatsMax") Integer seatsMax,
            @Param("gearType") GearType gearType,
            @Param("fuelType") FuelType fuelType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("priceSort") String priceSort,
            @Param("horsepowerSort") String horsepowerSort,
            @Param("buildYearSort") String buildYearSort
    );


}