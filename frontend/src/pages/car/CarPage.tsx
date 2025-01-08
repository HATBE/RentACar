import { Link, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Car } from '../../types/Car.ts';
import LoadingSpinner from '../../components/layout/LoadingSpinner.tsx';
import ErrorBanner from '../../components/layout/banner/ErrorBanner.tsx';
import { Booking } from '../../types/Booking.ts';
import CarsApi from '../../services/CarsApi.ts';
import BookingsApi from '../../services/BookingsApi.ts';
import CarBookingForm from '../../components/booking/carBooking/CarBookingForm.tsx';
import CarSpecsGrid from '../../components/car/CarSpecsGrid.tsx';

export default function CarPage() {
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

  return (
    <>
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
                  alt={`Image of a ${car.make} ${car.model} ${car.buildYear}`}
                />
              </div>
            </div>
          </div>

          <div className="col-12 col-md-6">
            <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden">
              <div className="card-body">
                <div className="badge bg-secondary">{car.category.name}</div>
                <h2>
                  <b>{car.make}</b> {car.model} <i>{car.buildYear}</i>
                </h2>
              </div>
            </div>

            <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden mt-3">
              <div className="card-body">
                <div className="row g-2">
                  <CarSpecsGrid car={car} />
                </div>
                <div className="mt-4 mb-2 h4 fw-bold cyan-text-gradient text-center">
                  CHF {car.pricePerDay} / Day
                </div>
              </div>
            </div>

            <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden mt-3">
              <div className="card-body">
                <CarBookingForm bookings={bookings} car={car} />
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
