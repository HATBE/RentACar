import { Booking } from '../types/Booking.ts';

export default class BookingsService {
  static async getBookingsByCarId(carId: string, futureOnly: boolean = true): Promise<Booking[]> {
    const response = await fetch(
      `http://localhost:8081/api/v1/bookings/car/${carId}?future=${futureOnly}`
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

  static async getBookingById(bookingId: string): Promise<Booking | null> {
    const response = await fetch(`http://localhost:8081/api/v1/bookings/${bookingId}`);
    if (!response.ok) {
      throw new Error('Failed to fetch bookings');
    }

    return response.json();
  }

  static async postBooking(
    userId: number,
    carId: number,
    startDate: Date,
    endDate: Date
  ): Promise<Booking> {
    const response = await fetch(`http://localhost:8081/api/v1/bookings`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        userId: userId,
        carId: carId,
        startDate: startDate.toISOString(),
        endDate: endDate.toISOString(),
      }),
    });

    if (!response.ok) {
      const error =
        ((await response.json()) as { message: string }).message || 'Failed to post booking';
      throw new Error(error);
    }
    return response.json(); // Parse JSON response
  }
}
