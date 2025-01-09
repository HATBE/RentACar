import { Car } from '../types/Car.ts';
import { Booking } from '../types/Booking.ts';

export default class CarsApi {
  static async getCarById(carId: string): Promise<Car> {
    const response = await fetch(`http://localhost:8081/api/v1/cars/${carId}`);
    if (!response.ok) {
      throw new Error('Failed to fetch car');
    }
    return await response.json();
  }

  static async deleteCarById(carId: number) {
    const response = await fetch(`http://localhost:8081/api/v1/cars/${carId}`, {
      method: 'DELETE',
    });

    if (!response.ok) {
      const error =
        ((await response.json()) as { message: string }).message || 'Failed to delete car';
      throw new Error(error);
    }
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
    return await response.json();
  }

  static async updateCar(
    carId: string,
    make: string,
    model: string,
    buildYear: number,
    horsePower: number,
    seatCount: number,
    pricePerDay: number,
    gearType: string,
    fuelType: string,
    categoryId: number
  ): Promise<Car> {
    const response = await fetch(`http://localhost:8081/api/v1/cars/${carId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        make: make,
        model: model,
        buildYear: buildYear,
        horsePower: horsePower,
        seatsCount: seatCount,
        pricePerDay: pricePerDay,
        gearType: gearType,
        fuelType: fuelType,
        categoryId: categoryId,
      }),
    });

    if (!response.ok) {
      const errors = (await response.json()) as { errors: string[] };

      if(!errors.errors) {
        throw new Error('Failed to update car');
      }

      const error = errors.errors.join(',\n ') || 'Failed to update car';
      throw new Error(error);
    }
    return await response.json();
  }

  static async postCar(
    make: string,
    model: string,
    buildYear: number,
    horsePower: number,
    seatCount: number,
    pricePerDay: number,
    gearType: string,
    fuelType: string,
    categoryId: number
  ): Promise<Booking> {
    const response = await fetch(`http://localhost:8081/api/v1/cars`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        make: make,
        model: model,
        buildYear: buildYear,
        horsePower: horsePower,
        seatsCount: seatCount,
        pricePerDay: pricePerDay,
        gearType: gearType,
        fuelType: fuelType,
        categoryId: categoryId,
      }),
    });

    if (!response.ok) {
        const errors = (await response.json()) as { errors: string[] };

        if(!errors.errors) {
          throw new Error('Failed to post car');
        }

      const error = errors.errors.join(',\n ') || 'Failed to post car';
      throw new Error(error);
    }
    return await response.json();
  }
}
