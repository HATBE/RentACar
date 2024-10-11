import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Car } from '../ types/Car.ts';
import LoadingSpinner from '../components/LoadingSpinner.tsx';

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
          throw new Error('Failed to fetch car');
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

  if (loading) {
    return (
      <div>
        <LoadingSpinner />
      </div>
    );
  }

  if (error || car === null) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      {car.make}
      {car.model}
      {car.buildYear}
    </div>
  );
}
