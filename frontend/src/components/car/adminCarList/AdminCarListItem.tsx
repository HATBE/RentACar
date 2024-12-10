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
      onCarDeleted(car.id);
    } catch (error) {
      alert(`Failed to delete car: ${(error as Error).message}`);
    }
  };

  return (
    <tr>
      <td>{car.id}</td>

      <td>{car.category.name}</td>

      <td className="text-nowrap w-100">
        <Link className="link-light text-decoration-none" to={`/admin/cars/${car.id}`}>
          {car.make} {car.model} {car.buildYear}
        </Link>
      </td>

      <td style={{ minWidth: '250px' }}>
        <Link to={`/admin/cars/${car.id}`}>
          <button className="btn btn-sm btn-success">
            <i className="bi bi-eye-fill me-2"></i>View
          </button>
        </Link>
        <Link to={`/admin/cars/${car.id}/edit`}>
          <button className="ms-2 btn btn-sm btn-primary">
            <i className="bi bi-pen-fill me-2"></i>Edit
          </button>
        </Link>
        <button onClick={handleDelete} className="ms-2 btn-sm btn btn-danger">
          <i className="bi bi-trash-fill me-2"></i> Delete
        </button>
      </td>
    </tr>
  );
}
