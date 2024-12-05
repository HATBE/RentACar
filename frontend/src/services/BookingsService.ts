import { Booking } from '../types/Booking.ts';
import { DateRange } from '../types/DateRage.ts';

export default class BookingsService {
  static calculateDaysFromDateRange(startDate: Date, endDate: Date): number {
    const timeDiff = Math.abs(endDate.getTime() - startDate.getTime());
    return Math.ceil(timeDiff / (1000 * 60 * 60 * 24)) + 1;
  }

  static isDateBooked(date: Date, dates: DateRange[]): boolean {
    const day = date.setHours(0, 0, 0, 0); // 00:00:00
    return dates.some(({ start, end }) => day >= start && day <= end);
  }

  static getDateRangesFromBookings(bookings: Booking[]): DateRange[] {
    return bookings.map((booking) => ({
      start: new Date(booking.startDate).setHours(0, 0, 0, 0), // 00:00:00 // start of day
      end: new Date(booking.endDate).setHours(23, 59, 59, 999), // 23:59:59:999 // end of day
    }));
  }
}
