import { CarCategory } from '../types/CarCategory.ts';

export default class CarCarCategoriesApi {
  static async getCarCategories(): Promise<CarCategory[]> {
    const response = await fetch('http://localhost:8081/api/v1/carcategories');
    if (!response.ok) {
      throw new Error('Failed to fetch car categories');
    }
    return await response.json();
  }
}
