import { ChangeEvent, FormEvent, useEffect, useState } from 'react';
import CarService from '../../services/CarsApi.ts';
import CarCarCategoriesService from '../../services/CarCategoriesApi.ts';
import ErrorBanner from '../../components/banner/ErrorBanner.tsx';
import SuccessBanner from '../../components/banner/SuccessBanner.tsx';
import LoadingSpinner from '../../components/LoadingSpinner.tsx';
import { CarCategory } from '../../types/CarCategory.ts';
import { useNavigate, useParams } from 'react-router-dom';

export default function AdminEditCarPage() {
  const navigate = useNavigate();
  const { carId } = useParams<{ carId: string }>();

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

  const fetchCarDetails = async () => {
    try {
      setLoading(true);
      const car = await CarService.getCarById(carId!);
      setFormData({
        make: car.make,
        model: car.model,
        buildYear: car.buildYear.toString(),
        horsePower: car.horsepower.toString(),
        seatCount: car.seatsCount.toString(),
        pricePerDay: car.pricePerDay.toString(),
        gearType: car.gearType,
        fuelType: car.fuelType,
        category: car.category.id,
      });
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  };

  const fetchOptions = async () => {
    try {
      const [optionsData, carCategoriesData] = await Promise.all([
        CarService.getCarOptions(),
        CarCarCategoriesService.getCarCategories(),
      ]);
      setGearTypes(optionsData.gearTypes);
      setFuelTypes(optionsData.fuelTypes);
      setCategories(carCategoriesData);
    } catch (err) {
      console.error('Error fetching data:', (err as Error).message);
    }
  };

  useEffect(() => {
    fetchOptions();
    fetchCarDetails();
  }, [carId]);

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
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
      await CarService.updateCar(
        carId!,
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
      setSuccess('Successfully updated car');
      navigate(`/cars/${carId}`);
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
        {error && <ErrorBanner message={error} />}
        {success && <SuccessBanner message={success} />}

        <form onSubmit={handleSubmit}>
          <h2>Edit Car</h2>
          <div className="mb-3">
            <label>Make</label>
            <input
              type="text"
              className="form-control"
              name="make"
              value={formData.make}
              onChange={handleChange}
            />
          </div>
          <div className="mb-3">
            <label>Model</label>
            <input
              type="text"
              className="form-control"
              name="model"
              value={formData.model}
              onChange={handleChange}
            />
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
              value={formData.buildYear}
              onChange={handleChange}
            />
          </div>
          <div className="mb-3">
            <label>Horse Power</label>
            <input
              type="number"
              className="form-control"
              name="horsePower"
              value={formData.horsePower}
              onChange={handleChange}
            />
          </div>
          <div className="mb-3">
            <label>Seat count</label>
            <input
              type="number"
              className="form-control"
              name="seatCount"
              value={formData.seatCount}
              onChange={handleChange}
            />
          </div>
          <div className="mb-3">
            <label>Price per day</label>
            <input
              type="number"
              className="form-control"
              name="pricePerDay"
              value={formData.pricePerDay}
              onChange={handleChange}
            />
          </div>
          <div className="mb-3">
            <label>Gear Type</label>
            <select
              className="form-select"
              name="gearType"
              value={formData.gearType}
              onChange={handleChange}
            >
              <option disabled={true} value="">
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
            <select
              className="form-select"
              name="fuelType"
              value={formData.fuelType}
              onChange={handleChange}
            >
              <option disabled={true} value="">
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
            <select
              className="form-select"
              name="category"
              value={formData.category}
              onChange={handleChange}
            >
              <option disabled={true} value="">
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
            {!loading && 'Update Car'}
          </button>
        </form>
      </div>
    </div>
  );
}
