import { Booking } from '../types/Booking.ts';

export default class BookingsService {
  static async getBookingsByCarId(carId: string, futureOnly: boolean = true): Promise<Booking[]> {
    const response = await fetch(
      `http://localhost:8080/api/v1/bookings/car/${carId}?future=${futureOnly}`
    );
    if (!response.ok) {
      throw new Error('Failed to fetch bookings');
    }
    // if NO CONTENT then it must be empty
    if (response.status === 204) {
      return [];
    }
    return response.json();
  }
}
