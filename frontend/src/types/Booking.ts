import { Car } from './Car.ts';

export type Booking = {
  id: number;
  //user: null; // TODO:
  car: Car;
  startDate: Date;
  endDate: Date;
  creationDate: Date;
  calculatedPrice: number;
};
