package ch.hatbe.soeproject.utils;

import java.time.LocalDate;

public class CarPriceCalculator {
    public static float calculateCarPricePerDay(float carPricePerDay, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return 0.0f;
        }
        return carPricePerDay * (endDate.toEpochDay() - startDate.toEpochDay() + 1);
    }
}
