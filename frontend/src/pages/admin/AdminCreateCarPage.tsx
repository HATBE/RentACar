import { useEffect, useState } from 'react';
import CarService from '../../services/CarsApi.ts';
import CarCarCategoriesService from '../../services/CarCategoriesApi.ts';

export default function AdminCreateCarPage() {
  const [gearTypes, setGearTypes] = useState<string[]>([]);
  const [fuelTypes, setFuelTypes] = useState<string[]>([]);
  const [categories, setCategories] = useState<string[]>([]);

  const fetchData = async () => {
    try {
      const [optionsData, carCategoriesDate] = await Promise.all([
        CarService.getCarOptions(),
        CarCarCategoriesService.getCarCategories(),
      ]);

      setGearTypes(optionsData.gearTypes);
      setFuelTypes(optionsData.fuelTypes);
      setCategories(carCategoriesDate);
    } catch (err) {
      console.error('Error fetching data:', (err as Error).message);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="row">
      <form className="col-12 col-xl-4 offset-0 offset-xl-4">
        <h2>Create a Car</h2>
        <div className="mb-3">
          <label>Make</label>
          <input type="text" className="form-control" />
        </div>
        <div className="mb-3">
          <label>Model</label>
          <input type="text" className="form-control" />
        </div>
        <div className="mb-3">
          <label>Build Year</label>
          <input type="year" className="form-control" />
        </div>
        <div className="mb-3">
          <label>Horse Power</label>
          <input type="number" className="form-control" />
        </div>
        <div className="mb-3">
          <label>Seat count</label>
          <input type="number" className="form-control" />
        </div>
        <div className="mb-3">
          <label>Price per day</label>
          <input type="number" className="form-control" />
        </div>
        <div className="mb-3">
          <label>Gear Type</label>
          <select className="form-select">
            <option selected={true} disabled={true}>
              Select a gear type
            </option>
            {gearTypes.map((gearType) => (
              <option key={gearType} value={gearType}>
                {gearType}
              </option>
            ))}
          </select>
        </div>
        <div className="mb-3">
          <label>Fuel Type</label>
          <select className="form-select">
            <option selected={true} disabled={true}>
              Select a fuel type
            </option>
            {fuelTypes.map((fuelType) => (
              <option key={fuelType} value={fuelType}>
                {fuelType}
              </option>
            ))}
          </select>
        </div>
        <div className="mb-3">
          <label>Category</label>
          <select className="form-select">
            <option disabled={true} selected={true}>
              Select a category
            </option>
            {categories.map((category) => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </select>
        </div>
        <button className="btn btn-success">Create</button>
      </form>
    </div>
  );
}
