import BookingCalendar from '../bookingCalendar/BookingCalendar.tsx';
import { Booking } from '../../../types/Booking.ts';
import { Car } from '../../../types/Car.ts';
import { useRef, useState } from 'react';
import BookingsApi from '../../../services/BookingsApi.ts';
import ErrorBanner from '../../banner/ErrorBanner.tsx';
import { useNavigate } from 'react-router-dom';
import LoadingSpinner from '../../LoadingSpinner.tsx';
import BookingsService from '../../../services/BookingsService.ts';

type CarBookingProps = {
  bookings: Booking[];
  car: Car;
};

export default function CarBookingForm({ bookings, car }: CarBookingProps) {
  const navigate = useNavigate();

  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [daysSelected, setDaysSelected] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  const clearDatesCallback = useRef<() => void>();

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
      setLoading(true);

      if (clearDatesCallback.current) {
        clearDatesCallback.current();
      }

      const normalizedStartDate = BookingsService.normalizeDate(startDate, 0, 0, 0);
      const normalizedEndDate = BookingsService.normalizeDate(endDate, 23, 59, 59);

      try {
        const booking = await BookingsApi.postBooking(
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
      } finally {
        setLoading(false);
      }
    }
  };

  const priceForBookedDays = car.pricePerDay * (daysSelected || 1);

  return (
    <>
      {error && <ErrorBanner message={error} />}

      <BookingCalendar
        onClearDates={(clearCallback) => (clearDatesCallback.current = clearCallback)}
        bookings={bookings}
        selectDatesCallback={selectDatesCallback}
      />
      <button disabled={!canBook()} onClick={handleBooking} className="btn btn-primary w-100 mt-3">
        {loading && <LoadingSpinner />}

        {!loading && (
          <>
            <i className="bi bi-cart-fill"></i> Book this Car for <b>CHF {priceForBookedDays}</b> (
            {!daysSelected ? 1 : daysSelected} day
            {daysSelected && daysSelected > 1 ? 's' : ''})
          </>
        )}
      </button>
    </>
  );
}
