package ch.hatbe.soeproject.service.carCategory;

import ch.hatbe.soeproject.persistance.entities.CarCategory;
import ch.hatbe.soeproject.persistance.repositories.CarCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarCategoryServiceImpl implements CarCategoryService {
    private final CarCategoryRepository carCategoryRepository;

    public CarCategoryServiceImpl(CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

    public List<CarCategory> getAllCarCategories() {
        return carCategoryRepository.findAll();
    }
}
