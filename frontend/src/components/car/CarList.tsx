import CarListItem from './CarListItem.tsx';
import { useEffect, useState } from 'react';
import { Car } from '../../ types/Car.ts';
import LoadingSpinner from '../LoadingSpinner.tsx';

export default function CarList() {
  const [cars, setCars] = useState<Car[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCars = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/v1/cars');
        if (!response.ok) {
          throw new Error('Failed to fetch cars');
        }
        const data: Car[] = await response.json();
        setCars(data);
        setLoading(false);
      } catch (err) {
        setError((err as Error).message);
        setLoading(false);
      }
    };

    fetchCars();
  }, []);

  if (loading) {
    return (
      <div>
        <LoadingSpinner />
      </div>
    );
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <div>FILTERBAR</div>
      <div className="row g-3">
        {cars.map((car) => (
          <CarListItem key={car.id} car={car} />
        ))}
      </div>
    </div>
  );
}
