import { Link } from 'react-router-dom';
import { Car } from '../../../types/Car.ts';
import CarSpecsGrid from '../CarSpecsGrid.tsx';

type CarListItemProps = {
  car: Car;
  showPrice: boolean;
};

export default function CarListItem({ car, showPrice }: CarListItemProps) {
  return (
    <>
      <Link className="car-item link-dark text-decoration-none" to={`/cars/${car.id}`}>
        <div className="card bg-dark  text-light border-0 shadow-lg overflow-hidden">
          <div className="card-body h-100 p-0">
            <img
              className="user-select-none thumpnail h-100"
              src={`/assets/img/${car.category.image}`}
              alt={`Image of a ${car.make} ${car.model} ${car.buildYear}`}
            />
            <div className="p-3">
              <div
                style={{ height: '50px' }}
                className="text-center h5 fw-bold d-flex align-items-center justify-content-center"
              >
                {car.make} {car.model} {car.buildYear}
              </div>
              <CarSpecsGrid car={car} />
              {showPrice && (
                <div className="mt-4 mb-2 h4 fw-bold cyan-text-gradient text-center">
                  CHF {car.pricePerDay} / Day
                </div>
              )}
            </div>
          </div>
        </div>
      </Link>
    </>
  );
}
