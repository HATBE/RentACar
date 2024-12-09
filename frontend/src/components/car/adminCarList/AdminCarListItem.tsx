import { Car } from '../../../types/Car.ts';
import CarsApi from '../../../services/CarsApi.ts';
import { Link } from 'react-router-dom';

type CarListItemProps = {
  car: Car;
  onCarDeleted: (carId: number) => void; // New callback prop
};

export default function AdminCarListItem({ car, onCarDeleted }: CarListItemProps) {
  const handleDelete = async () => {
    const isConfirmed = confirm(
      `Do you really want to delete car #${car.id} / ${car.make} ${car.model} ${car.buildYear}`
    );

    if (!isConfirmed) return;

    try {
      await CarsApi.deleteCarById(car.id);
      alert(`Car #${car.id} deleted successfully.`);
      onCarDeleted(car.id); // Notify parent about the deletion
    } catch (error) {
      alert(`Failed to delete car: ${(error as Error).message}`);
    }
  };

  return (
    <tr>
      <td>{car.id}</td>
      <td style={{ minWidth: '150px' }}>
        <Link to={`/cars/${car.id}/edit`}>
          <button className="btn btn-primary">Edit</button>
        </Link>
        <button onClick={handleDelete} className="ms-2 btn btn-danger">
          Delete
        </button>
      </td>
      <td>
        <img
          style={{ maxWidth: '100px' }}
          className="user-select-none thumbnail h-100"
          src={`/assets/img/${car.category.image}`}
          alt={`Image of a ${car.make} ${car.model} ${car.buildYear}`}
        />
      </td>
      <td>
        {car.make} {car.model} {car.buildYear}
      </td>
      <td className="w-100"></td>
    </tr>
  );
}
