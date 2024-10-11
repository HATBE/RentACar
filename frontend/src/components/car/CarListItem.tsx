import { Link } from 'react-router-dom';
import { Car } from '../../ types/Car.ts';

type CarListItemProps = {
  car: Car;
};

export default function CarListItem({ car }: CarListItemProps) {
  return (
    <div className="col-12 col-md-4 col-xl-3">
      <Link className="car-item link-dark text-decoration-none" to={`/cars/${car.id}`}>
        <div className="card border-0 shadow-lg overflow-hidden">
          <div className="card-body p-0">
            <img className="user-select-none thumpnail" src="/assets/img/car.jpg" alt="" />
            <div className="p-3">
              <div className="text-center h5 fw-bold">
                {car.make} {car.model} {car.buildYear}
              </div>
              <div className="row g-2 mt-2">
                <div className="col-6">
                  <div className="bg-dark rounded text-light px-2 py-1">
                    <i className="bi bi-speedometer me-2"></i>
                    <span>{car.horsepower} PS</span>
                  </div>
                </div>
                <div className="col-6 ">
                  <div className="bg-dark rounded text-light px-2 py-1">
                    <i className="bi bi-person-fill me-2"></i>
                    <span>{car.seatsCount}</span>
                  </div>
                </div>
                <div className="col-6 ">
                  <div className="bg-dark rounded text-light px-2 py-1">
                    <i className="bi bi-arrow-down-up me-2"></i>
                    <span>{car.gearType}</span>
                  </div>
                </div>
                <div className="col-6">
                  <div className="bg-dark rounded text-light px-2 py-1">
                    <i className="bi bi-fuel-pump me-2"></i>
                    <span>{car.fuelType}</span>
                  </div>
                </div>
              </div>
              <div className="mt-4 mb-2 h4 fw-bold cyan-text-gradient text-center">
                CHF {car.pricePerDay} / Tag
              </div>
            </div>
          </div>
        </div>
      </Link>
    </div>
  );
}
