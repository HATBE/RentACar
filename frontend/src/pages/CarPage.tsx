import { Link, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Car } from '../types/Car.ts';
import LoadingSpinner from '../components/LoadingSpinner.tsx';
import ErrorBanner from '../components/ErrorBanner.tsx';
import { Booking } from '../types/Booking.ts';
import BookingCalendar from '../components/booking/BookingCalendar.tsx';
import CarService from '../services/CarsService.ts';
import BookingsService from '../services/BookingsService.ts';

export default function CarPage() {
  const { carid } = useParams();

  const [car, setCar] = useState<Car | null>(null);
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchData = async () => {
    try {
      const [carData, bookingsData] = await Promise.all([
        CarService.getCarById(carid!),
        BookingsService.getBookingsByCarId(carid!, true),
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
  }, [carid]);

  return (
    <div>
      {error && <ErrorBanner message={error} />}
      {loading && <LoadingSpinner />}

      {car && !error && !loading && (
        <div className="row g-3">
          <div className="col-12">
            <Link to="/">
              <button className="btn btn-secondary">
                <i className="bi bi-arrow-left"></i> Back
              </button>
            </Link>
          </div>
          <div className="col-12 col-md-6">
            <div className="card bg-dark  text-light border-0 shadow-lg overflow-hidden">
              <div className="card-body p-0">
                <img
                  src={`/assets/img/${car.category.image}`}
                  className="thumpnail"
                  alt={`Image of a ${car.model}`}
                />
              </div>
            </div>
          </div>
          <div className="col-12 col-md-6">
            <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden">
              <div className="card-body">
                <h2>
                  <b>{car.make}</b> {car.model} <i>{car.buildYear}</i>
                </h2>
              </div>
            </div>

            <button className="btn btn-primary w-100 mt-3">
              <i className="bi bi-cart-fill"></i> Book this Car for <b>CHF {car.pricePerDay}</b> a
              day
            </button>

            <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden mt-3">
              <div className="card-body">
                <div className="row g-2">
                  <div className="col-6">
                    <div className="bg-light rounded text-dark px-2 py-1">
                      <i className="bi bi-speedometer me-2"></i>
                      <span>{car.horsepower} HP</span>
                    </div>
                  </div>
                  <div className="col-6 ">
                    <div className="bg-light rounded text-dark px-2 py-1">
                      <i className="bi bi-person-fill me-2"></i>
                      <span>{car.seatsCount}</span>
                    </div>
                  </div>
                  <div className="col-6 ">
                    <div className="bg-light rounded text-dark px-2 py-1">
                      <i className="bi bi-arrow-down-up me-2"></i>
                      <span>{car.gearType}</span>
                    </div>
                  </div>
                  <div className="col-6">
                    <div className="bg-light rounded text-dark px-2 py-1">
                      <i className="bi bi-fuel-pump me-2"></i>
                      <span>{car.fuelType}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden mt-3">
              <div className="card-body">
                {bookings.length > 0 && <BookingCalendar bookings={bookings} />}
                {bookings.length === 0 && <span>This car has no bookings yet</span>}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
