package ch.hatbe.soeproject.persistance.cars;

import ch.hatbe.soeproject.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByMakeAndBuildYear(@Param("make") String make, @Param("buildYear") Integer buildYear);
}
