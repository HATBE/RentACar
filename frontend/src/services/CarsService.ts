import { Car } from '../types/Car.ts';

export default class CarService {
  static async getCarById(carId: string): Promise<Car> {
    const response = await fetch(`http://localhost:8080/api/v1/cars/${carId}`);
    if (!response.ok) {
      throw new Error('Failed to fetch car');
    }
    return response.json();
  }

  static async getCars(queryParams: URLSearchParams): Promise<Car[]> {
    const response = await fetch(`http://localhost:8080/api/v1/cars?${queryParams.toString()}`);
    if (!response.ok) {
      throw new Error('There are no cars matching yor search!');
    }
    return response.json();
  }

  static async getCarOptions(): Promise<{ gearTypes: string[]; fuelTypes: string[] }> {
    const response = await fetch('http://localhost:8080/api/v1/cars/options');
    if (!response.ok) {
      throw new Error('Failed to fetch car options');
    }
    return response.json();
  }
}
