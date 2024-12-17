package ch.hatbe.soeproject.service.carCategory;

import ch.hatbe.soeproject.persistance.entities.CarCategory;
import ch.hatbe.soeproject.persistance.repositories.CarCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarCategoryServiceImpl implements CarCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CarCategoryServiceImpl.class);

    private final CarCategoryRepository carCategoryRepository;

    public CarCategoryServiceImpl(CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

    public List<CarCategory> getAllCarCategories() {
        logger.debug("Fetching all car categories");
        List<CarCategory> categories = carCategoryRepository.findAll();
        logger.debug("Retrieved {} car categories", categories.size());
        return categories;
    }
}
