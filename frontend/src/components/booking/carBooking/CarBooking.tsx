import BookingCalendar from '../bookingCalendar/BookingCalendar.tsx';
import { Booking } from '../../../types/Booking.ts';
import { Car } from '../../../types/Car.ts';
import { useRef, useState } from 'react';
import BookingsService from '../../../services/BookingsService.ts';
import ErrorBanner from '../../banner/ErrorBanner.tsx';
import SuccessBanner from '../../banner/SuccessBanner.tsx';

type CarBookingProps = {
  bookings: Booking[];
  car: Car;
};

export default function CarBooking({ bookings, car }: CarBookingProps) {
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  const clearDatesFn = useRef<() => void>();

  const selectDatesCallback = (startDate: Date, endDate: Date) => {
    setStartDate(startDate);
    setEndDate(endDate);
  };

  const canBook = () => {
    return startDate && endDate;
  };

  const handleBooking = async () => {
    if (startDate && endDate) {
      if (clearDatesFn.current) {
        clearDatesFn.current();
      }

      console.log(`Booking car ${car.id} from ${startDate} to ${endDate}`);
      
      try {
        const booking = await BookingsService.postBooking(1, car.id, startDate, endDate);

        bookings.push(booking);
        setError(null);
        setSuccess('Booking successful');
      } catch (err) {
        setSuccess(null);
        setError((err as Error).message);
      }
    }
  };

  return (
    <>
      {error && <ErrorBanner message={error} />}
      {success && <SuccessBanner message={success} />}

      <BookingCalendar
        onClearDates={(clearFn) => (clearDatesFn.current = clearFn)}
        bookings={bookings}
        selectDatesCallback={selectDatesCallback}
      />
      <button disabled={!canBook()} onClick={handleBooking} className="btn btn-primary w-100 mt-3">
        <i className="bi bi-cart-fill"></i> Book this Car for <b>CHF {car.pricePerDay}</b> a day
      </button>
    </>
  );
}
