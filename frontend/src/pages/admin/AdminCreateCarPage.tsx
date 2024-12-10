import { ChangeEvent, FormEvent, useEffect, useState } from 'react';
import CarService from '../../services/CarsApi.ts';
import CarCarCategoriesService from '../../services/CarCategoriesApi.ts';
import LoadingSpinner from '../../components/layout/LoadingSpinner.tsx';
import { CarCategory } from '../../types/CarCategory.ts';
import { useNavigate } from 'react-router-dom';
import ErrorBanner from '../../components/layout/banner/ErrorBanner.tsx';
import SuccessBanner from '../../components/layout/banner/SuccessBanner.tsx';

export default function AdminCreateCarPage() {
  const navigate = useNavigate();

  const [gearTypes, setGearTypes] = useState<string[]>([]);
  const [fuelTypes, setFuelTypes] = useState<string[]>([]);
  const [categories, setCategories] = useState<CarCategory[]>([]);

  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  const [formData, setFormData] = useState({
    make: '',
    model: '',
    buildYear: '',
    horsePower: '',
    seatCount: '',
    pricePerDay: '',
    gearType: '',
    fuelType: '',
    category: -1,
  });

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

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const clearForm = () => {
    setFormData({
      make: '',
      model: '',
      buildYear: '',
      horsePower: '',
      seatCount: '',
      pricePerDay: '',
      gearType: '',
      fuelType: '',
      category: -1,
    });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    if (!isFormValid()) {
      setError('Please fill out all fields');
      setLoading(false);
      return;
    }

    try {
      const car = await CarService.postCar(
        formData.make,
        formData.model,
        +formData.buildYear,
        +formData.horsePower,
        +formData.seatCount,
        +formData.pricePerDay,
        formData.gearType,
        formData.fuelType,
        formData.category
      );
      setError(null);
      setSuccess('Successfully created car');
      clearForm();
      navigate(`/cars/${car.id}`);
    } catch (err) {
      setSuccess(null);
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  };

  const isFormValid = () => {
    return !(
      formData.category === -1 ||
      !formData.category ||
      !formData.gearType ||
      !formData.fuelType ||
      !formData.make ||
      !formData.model ||
      !formData.buildYear ||
      !formData.horsePower ||
      !formData.seatCount ||
      !formData.pricePerDay
    );
  };

  return (
    <div className="row">
      <div className="col-12 col-xl-4 offset-0 offset-xl-4">
        <div className="card bg-dark  text-light border-0 shadow-lg overflow-hidden">
          <h4 className="card-header">Create a car</h4>
          <div className="card-body">
            <form onSubmit={handleSubmit}>
              {error && <ErrorBanner message={error} />}
              {success && <SuccessBanner message={success} />}
              <div className="mb-3">
                <label>Make</label>
                <input type="text" className="form-control" name="make" onChange={handleChange} />
              </div>
              <div className="mb-3">
                <label>Model</label>
                <input type="text" className="form-control" name="model" onChange={handleChange} />
              </div>
              <div className="mb-3">
                <label>Build Year</label>
                <input
                  type="number"
                  min="1900"
                  max="3000"
                  step="1"
                  className="form-control"
                  name="buildYear"
                  onChange={handleChange}
                />
              </div>
              <div className="mb-3">
                <label>Horse Power</label>
                <input
                  type="number"
                  className="form-control"
                  name="horsePower"
                  onChange={handleChange}
                />
              </div>
              <div className="mb-3">
                <label>Seat count</label>
                <input
                  type="number"
                  className="form-control"
                  name="seatCount"
                  onChange={handleChange}
                />
              </div>
              <div className="mb-3">
                <label>Price per day</label>
                <input
                  type="number"
                  className="form-control"
                  name="pricePerDay"
                  onChange={handleChange}
                />
              </div>
              <div className="mb-3">
                <label>Gear Type</label>
                <select className="form-select" name="gearType" onChange={handleChange}>
                  <option selected={true} disabled={true} value="">
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
                <select className="form-select" name="fuelType" onChange={handleChange}>
                  <option selected={true} disabled={true} value="">
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
                <select className="form-select" name="category" onChange={handleChange}>
                  <option selected={true} disabled={true} value="">
                    Select a category
                  </option>
                  {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
              </div>
              <button disabled={!isFormValid()} className="btn btn-success" type="submit">
                {loading && <LoadingSpinner />}
                {!loading && 'Create'}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
