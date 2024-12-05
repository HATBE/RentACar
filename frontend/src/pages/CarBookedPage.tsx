import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Booking } from '../types/Booking.ts';
import BookingsService from '../services/BookingsService.ts';
import ErrorBanner from '../components/banner/ErrorBanner.tsx';
import LoadingSpinner from '../components/LoadingSpinner.tsx';

export default function CarBookedPage() {
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
          <div className="col-12 col-md-6 col-xl-4 offset-md-3 offset-md-4">
            <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden">
              <div className="card-body">
                <h3 className="text-center fw-bold mb-3">Booking Successful!</h3>

                <img
                  className="rounded user-select-none thumpnail h-100"
                  src={`/assets/img/${booking.car.category.image}`}
                  alt={`Image of a ${booking.car.make} ${booking.car.model} ${booking.car.buildYear}`}
                />
                <table className="table table-dark">
                  <thead></thead>
                  <tbody>
                    <tr>
                      <th>Car</th>
                      <td>
                        {booking.car.make} {booking.car.model} {booking.car.buildYear}
                      </td>
                    </tr>
                    <tr>
                      <th>Date</th>
                      <td>
                        {new Date(booking.startDate).toLocaleDateString()} -{' '}
                        {new Date(booking.endDate).toLocaleDateString()}
                      </td>
                    </tr>
                    <tr>
                      <th>Price</th>
                      <td>{booking.calculatedPrice} CHF.</td>
                    </tr>
                  </tbody>
                </table>
                <p>
                  You can pickup your car on{' '}
                  <i>{new Date(booking.startDate).toLocaleDateString()}</i> at the rental office.
                </p>

                <p>
                  <b>Rental office:</b>
                  <br />
                  RentACar AG
                  <br />
                  Street 7<br />
                  1234 City
                </p>

                <p>
                  You must return the car by <i>{new Date(booking.endDate).toLocaleDateString()}</i>{' '}
                  at 6 pm.
                </p>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
