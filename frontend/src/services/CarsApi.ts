import { Car } from '../types/Car.ts';
import { Booking } from '../types/Booking.ts';

export default class CarsApi {
  static async getCarById(carId: string): Promise<Car> {
    const response = await fetch(`http://localhost:8081/api/v1/cars/${carId}`);
    if (!response.ok) {
      throw new Error('Failed to fetch car');
    }
    return response.json();
  }

  static async deleteCarById(carId: number): Promise<Booking> {
    const response = await fetch(`http://localhost:8081/api/v1/cars/${carId}`, {
      method: 'DELETE',
    });

    if (!response.ok) {
      const error =
        ((await response.json()) as { message: string }).message || 'Failed to delete car';
      throw new Error(error);
    }
    return response.json();
  }

  static async getCars(queryParams?: URLSearchParams): Promise<Car[]> {
    const response = await fetch(
      `http://localhost:8081/api/v1/cars${queryParams ? `?${queryParams.toString()}` : ''}`
    );
    if (!response.ok) {
      throw new Error('There are no cars matching yor search!');
    }
    return response.json();
  }

  static async getCarOptions(): Promise<{ gearTypes: string[]; fuelTypes: string[] }> {
    const response = await fetch('http://localhost:8081/api/v1/cars/options');
    if (!response.ok) {
      throw new Error('Failed to fetch car options');
    }
    return response.json();
  }
}
