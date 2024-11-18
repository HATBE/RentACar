import { CarCategory } from './CarCategory.ts';

export type Car = {
  id: number;
  make: string;
  model: string;
  buildYear: number;
  seatsCount: number;
  horsepower: number;
  pricePerDay: number;
  gearType: string;
  fuelType: string;
  category: CarCategory;
}