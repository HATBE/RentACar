import CarListItem from './CarListItem.tsx';
import { useEffect, useState } from 'react';
import LoadingSpinner from '../LoadingSpinner.tsx';
import { useLocation } from 'react-router-dom';
import CarListFilterBar from './CarListFilterBar.tsx';
import ErrorBanner from '../ErrorBanner.tsx';
import { Car } from '../../types/Car.ts';
import CarService from '../../services/CarsService.ts';

export default function CarList() {
  const [cars, setCars] = useState<Car[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const location = useLocation();

  const fetchData = async () => {
    try {
      const queryParams = new URLSearchParams(location.search);
      const cars = await CarService.getCars(queryParams);
      setCars(cars);
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    // reload if location.search changes
  }, [location.search]);

  return (
    <div>
      <CarListFilterBar
        defaultValues={new URLSearchParams(location.search)}
        onApplyFilters={fetchData}
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
