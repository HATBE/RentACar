import { Car } from './Car.ts';

export type Booking = {
  id: number;
  car: Car;
  startDate: Date;
  endDate: Date;
  creationDate: Date;
  calculatedPrice: number;
  customerName: string
};
