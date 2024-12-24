import BookingsService from '../src/services/BookingsService';
import { DateRange } from '../src/types/DateRage';
import { Booking } from '../src/types/Booking';

describe('BookingsService', () => {
  describe('calculateDaysFromDateRange', () => {
    it('should calculate days between consecutive dates', () => {
      const startDate = new Date('2023-12-01');
      const endDate = new Date('2023-12-05');
      expect(BookingsService.calculateDaysFromDateRange(startDate, endDate)).toBe(5);
    });

    it('should return 1 for the same start and end date', () => {
      const startDate = new Date('2023-12-01');
      const endDate = new Date('2023-12-01');
      expect(BookingsService.calculateDaysFromDateRange(startDate, endDate)).toBe(1);
    });

    it('should handle dates spanning months and years', () => {
      const startDate = new Date('2023-12-30');
      const endDate = new Date('2024-01-02');
      expect(BookingsService.calculateDaysFromDateRange(startDate, endDate)).toBe(4);
    });
  });

  describe('isDateBooked', () => {
    it('should return true if the date is within a range', () => {
      const date = new Date('2023-12-02');
      const ranges: DateRange[] = [
        { start: new Date('2023-12-01').getTime(), end: new Date('2023-12-05').getTime() },
      ];
      expect(BookingsService.isDateBooked(date, ranges)).toBe(true);
    });

    it('should return false if the date is outside all ranges', () => {
      const date = new Date('2023-12-10');
      const ranges: DateRange[] = [
        { start: new Date('2023-12-01').getTime(), end: new Date('2023-12-05').getTime() },
      ];
      expect(BookingsService.isDateBooked(date, ranges)).toBe(false);
    });

    it('should return an empty array for no bookings', () => {
      const bookings: Booking[] = [];
      const ranges = BookingsService.getDateRangesFromBookings(bookings);
      expect(ranges).toEqual([]);
    });
  });

  describe('normalizeDate', () => {
    it('should normalize the date to the specified time', () => {
      const date = new Date('2023-12-01');
      const normalizedDate = BookingsService.normalizeDate(date, 15, 30, 0);
      expect(normalizedDate).toEqual(new Date(Date.UTC(2023, 11, 1, 15, 30, 0)));
    });

    it('should work across different months and years', () => {
      const date = new Date('2023-12-31');
      const normalizedDate = BookingsService.normalizeDate(date, 0, 0, 0);
      expect(normalizedDate).toEqual(new Date(Date.UTC(2023, 11, 31, 0, 0, 0)));
    });
  });
});
