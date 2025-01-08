import { Link, useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Car } from '../../types/Car.ts';
import { Booking } from '../../types/Booking.ts';
import CarsApi from '../../services/CarsApi.ts';
import BookingsApi from '../../services/BookingsApi.ts';
import ErrorBanner from '../../components/layout/banner/ErrorBanner.tsx';
import LoadingSpinner from '../../components/layout/LoadingSpinner.tsx';
import BookingCalendar from '../../components/booking/bookingCalendar/BookingCalendar.tsx';

export default function AdminCarDetailPage() {
  const navigate = useNavigate();

  const { carId } = useParams();

  const [car, setCar] = useState<Car | null>(null);
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchData = async () => {
    try {
      const [carData, bookingsData] = await Promise.all([
        CarsApi.getCarById(carId!),
        BookingsApi.getBookingsByCarId(carId!, true),
      ]);
      setCar(carData);
      setBookings(bookingsData);
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    // reload if carid changes
  }, [carId]);

  const handleDelete = async () => {
    if (!car) return;
    const isConfirmed = confirm(
      `Do you really want to delete car #${car.id} / ${car.make} ${car.model} ${car.buildYear}`
    );

    if (!isConfirmed) return;

    try {
      await CarsApi.deleteCarById(car.id);
      navigate('/admin');
    } catch (error) {
      alert(`Failed to delete car: ${(error as Error).message}`);
    }
  };

  return (
    <>
      {error && <ErrorBanner message={error} />}
      {loading && <LoadingSpinner />}

      {car && !error && !loading && (
        <div className="row g-3">
          <div className="col-12">
            <Link to={`/admin/cars/${car.id}/edit`}>
              <button className="btn btn-sm btn-primary">
                <i className="bi bi-pencil"></i> Edit
              </button>
            </Link>

            <button onClick={handleDelete} className="ms-2 btn-sm btn btn-danger">
              <i className="bi bi-trash"></i> Delete
            </button>

            <Link to={`/cars/${car.id}`}>
              <button className="ms-2 btn btn-sm btn-secondary">
                <i className="bi bi-eye"></i> Customer View
              </button>
            </Link>
          </div>
          <div className="col-12 col-md-6">
            <div className="card bg-dark  text-light border-0 shadow-lg overflow-hidden">
              <h4 className="card-header">
                {car.make} {car.model} {car.buildYear}
              </h4>
              <div className="card-body">
                <table className="table table-dark">
                  <thead></thead>
                  <tbody>
                    <tr>
                      <th>Category</th>
                      <td className="w-100">{car.category.name}</td>
                    </tr>
                    <tr>
                      <th>Horsepower</th>
                      <td className="w-100">{car.horsepower}</td>
                    </tr>
                    <tr>
                      <th>Gear Type</th>
                      <td className="w-100">{car.gearType}</td>
                    </tr>
                    <tr>
                      <th>Fuel Type</th>
                      <td className="w-100">{car.fuelType}</td>
                    </tr>
                    <tr>
                      <th className="text-nowrap">Price per day</th>
                      <td className="w-100">{car.pricePerDay} CHF.</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>

            <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden mt-3">
              <h4 className="card-header">
                <i className="bi bi-calendar"></i> Bookings
              </h4>
              <div className="card-body">
                {!bookings.length && <p>No bookings yet.</p>}
                {bookings.length > 0 && (
                  <table className="table table-dark">
                    <thead>
                      <tr>
                        <th>Date</th>
                        <th>Price</th>
                      </tr>
                    </thead>
                    <tbody>
                      {bookings.map((booking) => (
                        <tr key={booking.id}>
                          <td className="text-nowrap">
                            {new Date(booking.startDate).toLocaleDateString()} -{' '}
                            {new Date(booking.endDate).toLocaleDateString()}
                          </td>
                          <td className="w-100">{booking.calculatedPrice} CHF.</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
                <BookingCalendar bookings={bookings} disableDates={true} />
              </div>
            </div>
          </div>

          <div className="col-12 col-md-6">
            <div className="card bg-dark  text-light border-0 shadow-lg overflow-hidden">
              <div className="card-body p-0">
                <img
                  src={`/assets/img/${car.category.image}`}
                  className="thumpnail"
                  alt={`Image of a ${car.make} ${car.model} ${car.buildYear}`}
                />
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
