import { useEffect, useState } from 'react';
import { Car } from '../../../types/Car.ts';
import CarsApi from '../../../services/CarsApi.ts';
import ErrorBanner from '../../banner/ErrorBanner.tsx';
import LoadingSpinner from '../../LoadingSpinner.tsx';
import AdminCarListItem from './AdminCarListItem.tsx';

export default function AdminCarList() {
  const [cars, setCars] = useState<Car[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchData = async () => {
    try {
      const cars = await CarsApi.getCars();
      setCars(cars);
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleCarDeleted = (carId: number) => {
    setCars((prevCars) => prevCars.filter((car) => car.id !== carId));
  };

  return (
    <div>
      {error && <ErrorBanner message={error} />}
      {loading && <LoadingSpinner />}
      {!error && !loading && (
        <>
          <h2>Car List</h2>
          <table className="table table-center table-dark">
            <thead>
              <tr>
                <th>#</th>

                <th>Category</th>
                <th>Name</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {cars.map((car) => (
                <AdminCarListItem key={car.id} car={car} onCarDeleted={handleCarDeleted} />
              ))}
            </tbody>
          </table>
        </>
      )}
    </div>
  );
}
