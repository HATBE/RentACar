import BookingCalendar from '../bookingCalendar/BookingCalendar.tsx';
import { Booking } from '../../../types/Booking.ts';
import { Car } from '../../../types/Car.ts';
import { ChangeEvent, useRef, useState } from 'react';
import BookingsApi from '../../../services/BookingsApi.ts';
import ErrorBanner from '../../layout/banner/ErrorBanner.tsx';
import { useNavigate } from 'react-router-dom';
import LoadingSpinner from '../../layout/LoadingSpinner.tsx';
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
  const [customerName, setCustomerName] = useState<string>('');

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
    // Check if all required fields are filled, then "enable" book button
    return startDate && endDate && customerName;
  };

  const handleBooking = async () => {
    if (!canBook()) {
      return;
    }

    setLoading(true);

    if (clearDatesCallback.current) {
      clearDatesCallback.current();
    }

    const normalizedStartDate = BookingsService.normalizeDate(startDate!, 0, 0, 0);
    const normalizedEndDate = BookingsService.normalizeDate(endDate!, 23, 59, 59);

    try {
      const booking = await BookingsApi.postBooking(
        car.id,
        normalizedStartDate,
        normalizedEndDate,
        customerName
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
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setCustomerName(e.target.value);
  };

  const priceForBookedDays = car.pricePerDay * (daysSelected || 1);

  return (
    <>
      {error && <ErrorBanner message={error} />}

      <div className="mb-3">
        <label>Your name</label>
        <input type="text" className="form-control" name="customerName" onChange={handleChange} />
      </div>

      <BookingCalendar
        onClearDates={(clearCallback) => (clearDatesCallback.current = clearCallback)}
        bookings={bookings}
        selectDatesCallback={selectDatesCallback}
        disableDates={false}
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
