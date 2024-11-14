import CarListItem from './CarListItem.tsx';
import { useEffect, useState } from 'react';
import LoadingSpinner from '../LoadingSpinner.tsx';
import { useLocation } from 'react-router-dom';
import CarListFilterBar from './CarListFilterBar.tsx';
import ErrorBanner from '../ErrorBanner.tsx';
import { Car } from '../../types/Car.ts';

export default function CarList() {
  const [cars, setCars] = useState<Car[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const location = useLocation();

  const fetchCars = async () => {
    try {
      setLoading(true);
      setError(null);

      const queryParams = new URLSearchParams(location.search);

      const response = await fetch(`http://localhost:8080/api/v1/cars?${queryParams.toString()}`);

      if (!response.ok) {
        throw new Error('There are no cars matching yor search!');
      }

      const data: Car[] = await response.json();

      setCars(data);
      setLoading(false);
    } catch (err) {
      setError((err as Error).message);
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCars();
  }, [location.search]);

  return (
    <div>
      <CarListFilterBar
        defaultValues={new URLSearchParams(location.search)}
        onApplyFilters={fetchCars}
      />

      {error && <ErrorBanner message={error} />}
      {loading && <LoadingSpinner />}

      {!error && !loading && (
        <div className="row g-3">
          {cars.map((car) => (
            <CarListItem key={car.id} car={car} />
          ))}
        </div>
      )}
    </div>
  );
}
