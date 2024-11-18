package ch.hatbe.soeproject.persistance.repositories;

import ch.hatbe.soeproject.persistance.entities.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer> {}