import { Link, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Car } from '../ types/Car.ts';
import LoadingSpinner from '../components/LoadingSpinner.tsx';
import ErrorBanner from '../components/ErrorBanner.tsx';

export default function CarPage() {
  const { carid } = useParams();

  const [car, setCar] = useState<Car | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCars = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/v1/cars/${carid}`);
        if (!response.ok) {
          throw new Error(`No car with id ${carid}`);
        }
        const data: Car = await response.json();
        setCar(data);
        setLoading(false);
      } catch (err) {
        setError((err as Error).message);
        setLoading(false);
      }
    };

    fetchCars();
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
                <img src="/assets/img/car.jpg" className="thumpnail" />
              </div>
            </div>
          </div>
          <div className="col-12 col-md-6">
            <div className="card bg-dark text-light border-0 shadow-lg overflow-hidden">
              <div className="card-body">
                {car.make}&nbsp;
                {car.model}&nbsp;
                {car.buildYear}
              </div>
            </div>
            <button className="btn btn-primary w-100 mt-3">Book this Car</button>
          </div>
        </div>
      )}
    </div>
  );
}
