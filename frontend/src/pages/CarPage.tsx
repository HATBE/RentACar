import { Link, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Car } from '../types/Car.ts';
import LoadingSpinner from '../components/LoadingSpinner.tsx';
import ErrorBanner from '../components/ErrorBanner.tsx';
import { Booking } from '../types/Booking.ts';
import BookingCalendar from '../components/booking/BookingCalendar.tsx';

export default function CarPage() {
  const { carid } = useParams();

  const [car, setCar] = useState<Car | null>(null);
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchCar = async () => {
    fetch(`http://localhost:8080/api/v1/cars/${carid}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch car');
        }
        return response.json();
      })
      .then((data) => {
        const car: Car = data;
        setCar(car);
        setLoading(false);
      })
      .catch((error) => {
        setError((error as Error).message);
        setLoading(false);
      });
  };

  const fetchBookings = async () => {
    fetch(`http://localhost:8080/api/v1/bookings/car/${carid}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch bookings');
        }
        return response.json();
      })
      .then((data) => {
        const bookings: Booking[] = data;
        setBookings(bookings);
        setLoading(false);
      })
      .catch((error) => {
        setError((error as Error).message);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchCar();
    fetchBookings();
  }, []);

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
                <img src={`/assets/img/${car.category.image}`} className="thumpnail" />
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
            {bookings.length >= 0 && (
              <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden mt-3">
                <div className="card-body">
                  <BookingCalendar bookings={bookings} />
                </div>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
