import { useEffect, useState } from 'react';
import BookingsApi from '../../../services/BookingsApi.ts';
import { Booking } from '../../../types/Booking.ts';
import ErrorBanner from '../../layout/banner/ErrorBanner.tsx';
import LoadingSpinner from '../../layout/LoadingSpinner.tsx';
import AdminBookingListItem from './AdminBookingListItem.tsx';

export default function AdminBookingList() {
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchData = async () => {
    try {
      const bookings = await BookingsApi.getBookings();
      setBookings(bookings);
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      {error && <ErrorBanner message={error} />}
      {loading && <LoadingSpinner />}
      {!bookings && !error && !loading && <p>No bookings found</p>}
      {bookings && !error && !loading && (
        <>
          <table className="table table-center table-dark">
            <thead>
              <tr>
                <th>#</th>
                <th>Date</th>
                <th>Car</th>
                <th>Customer Name</th>
              </tr>
            </thead>
            <tbody>
              {bookings.map((booking) => (
                <AdminBookingListItem key={booking.id} booking={booking} />
              ))}
            </tbody>
          </table>
        </>
      )}
    </div>
  );
}