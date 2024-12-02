import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Booking } from '../types/Booking.ts';
import BookingsService from '../services/BookingsService.ts';
import ErrorBanner from '../components/banner/ErrorBanner.tsx';
import LoadingSpinner from '../components/LoadingSpinner.tsx';
import CarListItem from '../components/car/CarListItem.tsx';

export default function BookedCarPage() {
  const { bookingId } = useParams();

  const [booking, setBooking] = useState<Booking | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchData = async () => {
    try {
      const booking = await BookingsService.getBookingById(bookingId!);
      setBooking(booking);
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    // reload if bookingId changes
  }, [bookingId]);

  return (
    <div>
      {error && <ErrorBanner message={error} />}
      {loading && <LoadingSpinner />}

      {booking && !error && !loading && (
        <div className="row">
          <div className="col-12 col-md-6">
            Erfolgreich gebucht
            <br />
            {new Date(booking.startDate).toLocaleDateString()} -{' '}
            {new Date(booking.endDate).toLocaleDateString()}
            <br />
            {booking.calculatedPrice} CHF.
          </div>
          <div className="col-12 col-md-6">
            <CarListItem car={booking.car} showPrice={false} />
          </div>
        </div>
      )}
    </div>
  );
}
