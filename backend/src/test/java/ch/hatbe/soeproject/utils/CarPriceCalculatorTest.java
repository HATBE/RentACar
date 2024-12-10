package ch.hatbe.soeproject.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarPriceCalculatorTest {
    @Test
    public void testCalculateCarPricePerDayBasicRange() {
        float carPricePerDay = 50.0f;
        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 5); // 5 days
        float expected = carPricePerDay * 5; // 50 * 5 = 250.0
        assertEquals(expected, CarPriceCalculator.calculateCarPricePerDay(carPricePerDay, startDate, endDate), 0.01);
    }

    @Test
    public void testCalculateCarPricePerDaySingleDay() {
        float carPricePerDay = 50.0f;
        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 1); // 1 day
        float expected = carPricePerDay * 1; // 50 * 1 = 50.0
        assertEquals(expected, CarPriceCalculator.calculateCarPricePerDay(carPricePerDay, startDate, endDate), 0.01);
    }

    @Test
    public void testCalculateCarPricePerDayInvalidRange() {
        float carPricePerDay = 50.0f;
        LocalDate startDate = LocalDate.of(2023, 12, 5);
        LocalDate endDate = LocalDate.of(2023, 12, 1); // Invalid range
        float expected = 0.0f;
        assertEquals(expected, CarPriceCalculator.calculateCarPricePerDay(carPricePerDay, startDate, endDate), 0.01);
    }

    @Test
    public void testCalculateCarPricePerDayLargerRange() {
        float carPricePerDay = 75.0f;
        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 10); // 10 days
        float expected = carPricePerDay * 10; // 75 * 10 = 750.0
        assertEquals(expected, CarPriceCalculator.calculateCarPricePerDay(carPricePerDay, startDate, endDate), 0.01);
    }
}