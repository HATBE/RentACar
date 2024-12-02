import BookingCalendar from '../bookingCalendar/BookingCalendar.tsx';
import { Booking } from '../../../types/Booking.ts';
import { Car } from '../../../types/Car.ts';
import { useRef, useState } from 'react';
import BookingsService from '../../../services/BookingsService.ts';
import ErrorBanner from '../../banner/ErrorBanner.tsx';
import { useNavigate } from 'react-router-dom';

type CarBookingProps = {
  bookings: Booking[];
  car: Car;
};

export default function CarBooking({ bookings, car }: CarBookingProps) {
  const navigate = useNavigate();

  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [daysSelected, setDaysSelected] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  const clearDatesFn = useRef<() => void>();

  const selectDatesCallback = (startDate: Date, endDate: Date, days: number) => {
    setStartDate(startDate);
    setEndDate(endDate);
    setDaysSelected(days);
  };

  const clearDates = () => {
    setStartDate(null);
    setEndDate(null);
    setDaysSelected(null);
  };

  const canBook = () => {
    return startDate && endDate;
  };

  const handleBooking = async () => {
    if (startDate && endDate) {
      if (clearDatesFn.current) {
        clearDatesFn.current();
      }

      // Normalize to UTC midnight and end
      const normalizedStartDate = new Date(
        Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate(), 0, 0, 0)
      );
      const normalizedEndDate = new Date(
        Date.UTC(endDate.getFullYear(), endDate.getMonth(), endDate.getDate(), 23, 59, 59)
      );

      try {
        const booking = await BookingsService.postBooking(
          1,
          car.id,
          normalizedStartDate,
          normalizedEndDate
        );

        bookings.push(booking);

        setError(null);
        clearDates();
        navigate(`/cars/booked/${booking.id}`);
      } catch (err) {
        setError((err as Error).message);
      }
    }
  };

  return (
    <>
      {error && <ErrorBanner message={error} />}

      <BookingCalendar
        onClearDates={(clearFn) => (clearDatesFn.current = clearFn)}
        bookings={bookings}
        selectDatesCallback={selectDatesCallback}
      />
      <button disabled={!canBook()} onClick={handleBooking} className="btn btn-primary w-100 mt-3">
        <i className="bi bi-cart-fill"></i> Book this Car for{' '}
        <b>CHF {car.pricePerDay * (daysSelected || 1)}</b> ({!daysSelected ? 1 : daysSelected} day
        {daysSelected && daysSelected > 1 ? 's' : ''})
      </button>
    </>
  );
}
