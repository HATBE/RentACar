import { Car } from '../../types/Car.ts';

type CarSpecsGridProps = {
  car: Car;
};

export default function CarSpecsGrid({ car }: CarSpecsGridProps) {
  return (
    <div className="row g-2 mt-2">
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
  );
}
