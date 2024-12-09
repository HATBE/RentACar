package ch.hatbe.soeproject.utils;

import java.time.LocalDate;

public class CarPriceCalculator {
    public static float calculateCarPricePerDay(float carPricePerDay, LocalDate startDate, LocalDate endDate) {
        return carPricePerDay * (startDate.toEpochDay() - endDate.toEpochDay() + 1);
    }
}
