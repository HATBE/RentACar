import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CarService from '../../../services/CarsApi.ts';
import CarCarCategoriesService from '../../../services/CarCategoriesApi.ts';
import { CarCategory } from '../../../types/CarCategory.ts';

interface FilterBarProps {
  defaultValues: URLSearchParams;
  onApplyFilters: () => void;
}

export default function CarListFilterBar({ defaultValues }: FilterBarProps) {
  const navigate = useNavigate();

  const [buildYearFrom, setBuildYearFrom] = useState<string | null>(
    defaultValues.get('buildYearFrom')
  );
  const [buildYearTo, setBuildYearTo] = useState<string | null>(defaultValues.get('buildYearTo'));
  const [make, setMake] = useState<string | null>(defaultValues.get('make'));
  const [category, setCategory] = useState<string | null>(defaultValues.get('category'));
  const [priceMin, setPriceMin] = useState<string | null>(defaultValues.get('priceMin'));
  const [priceMax, setPriceMax] = useState<string | null>(defaultValues.get('priceMax'));
  const [seatsMin, setSeatsMin] = useState<string | null>(defaultValues.get('seatsMin'));
  const [seatsMax, setSeatsMax] = useState<string | null>(defaultValues.get('seatsMax'));
  const [gearType, setGearType] = useState<string | null>(defaultValues.get('gearType'));
  const [fuelType, setFuelType] = useState<string | null>(defaultValues.get('fuelType'));
  const [priceSort, setPriceSort] = useState<string | null>(defaultValues.get('priceSort'));
  const [horsepowerSort, setHorsepowerSort] = useState<string | null>(
    defaultValues.get('horsepowerSort')
  );
  const [buildYearSort, setBuildYearSort] = useState<string | null>(
    defaultValues.get('buildYearSort')
  );

  const [gearTypes, setGearTypes] = useState<string[]>([]);
  const [fuelTypes, setFuelTypes] = useState<string[]>([]);
  const [categories, setCategories] = useState<CarCategory[]>([]);

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

  const updateQueryString = () => {
    const params = new URLSearchParams();

    if (buildYearFrom) params.set('buildYearFrom', buildYearFrom);
    if (buildYearTo) params.set('buildYearTo', buildYearTo);
    if (make) params.set('make', make);
    if (category) params.set('category', category);
    if (priceMin) params.set('priceMin', priceMin);
    if (priceMax) params.set('priceMax', priceMax);
    if (seatsMin) params.set('seatsMin', seatsMin);
    if (seatsMax) params.set('seatsMax', seatsMax);
    if (gearType) params.set('gearType', gearType);
    if (fuelType) params.set('fuelType', fuelType);
    if (priceSort) params.set('priceSort', priceSort);
    if (horsepowerSort) params.set('horsepowerSort', horsepowerSort);
    if (buildYearSort) params.set('buildYearSort', buildYearSort);

    navigate(`?${params.toString()}`, { replace: true });
  };

  return (
    <div className="row g-3 mb-3">
      <h4>Filter Cars</h4>

      <div className="col-12 col-md-4 col-xl-2">
        <label>Build Year From: </label>
        <input
          placeholder="any"
          className="form-control"
          min="1900"
          max="2099"
          step="1"
          type="number"
          value={buildYearFrom || ''}
          onChange={(e) => setBuildYearFrom(e.target.value || null)}
        />
      </div>
      <div className="col-12 col-md-4 col-xl-2">
        <label>Build Year To: </label>
        <input
          placeholder="any"
          className="form-control"
          min="1900"
          max="2099"
          step="1"
          type="number"
          value={buildYearTo || ''}
          onChange={(e) => setBuildYearTo(e.target.value || null)}
        />
      </div>
      <div className="col-12 col-md-4 col-xl-2">
        <label>Make: </label>
        <input
          placeholder="any"
          className="form-control"
          type="text"
          value={make || ''}
          onChange={(e) => setMake(e.target.value || null)}
        />
      </div>
      <div className="col-12 col-md-4 col-xl-2">
        <label>Category: </label>
        <select
          className="form-select"
          value={category || ''}
          onChange={(e) => setCategory(e.target.value || null)}
        >
          <option value="">any</option>
          {categories.map((category) => (
            <option key={category.id} value={category.id}>
              {category.name}
            </option>
          ))}
        </select>
      </div>
      <div className="col-12 col-md-4 col-xl-2">
        <label>Price Min: </label>
        <input
          placeholder="any"
          className="form-control"
          type="number"
          value={priceMin || ''}
          onChange={(e) => setPriceMin(e.target.value || null)}
        />
      </div>
      <div className="col-12 col-md-4 col-xl-2">
        <label>Price Max: </label>
        <input
          placeholder="any"
          className="form-control"
          type="number"
          value={priceMax || ''}
          onChange={(e) => setPriceMax(e.target.value || null)}
        />
      </div>
      <div className="col-12 col-md-4 col-xl-2">
        <label>Seats Min: </label>
        <input
          placeholder="any"
          className="form-control"
          type="number"
          value={seatsMin || ''}
          onChange={(e) => setSeatsMin(e.target.value || null)}
        />
      </div>
      <div className="col-12 col-md-4 col-xl-2">
        <label>Seats Max: </label>
        <input
          placeholder="any"
          className="form-control"
          type="number"
          value={seatsMax || ''}
          onChange={(e) => setSeatsMax(e.target.value || null)}
        />
      </div>
      <div className="col-12 col-md-4 col-xl-2">
        <label>Gear Type: </label>
        <select
          className="form-select"
          value={gearType || ''}
          onChange={(e) => setGearType(e.target.value || null)}
        >
          <option value="">any</option>
          {gearTypes.map((type) => (
            <option key={type} value={type}>
              {type}
            </option>
          ))}
        </select>
      </div>

      <div className="col-12 col-md-4 col-xl-2">
        <label>Fuel Type: </label>
        <select
          className="form-select"
          value={fuelType || ''}
          onChange={(e) => setFuelType(e.target.value || null)}
        >
          <option value="">any</option>
          {fuelTypes.map((type) => (
            <option key={type} value={type}>
              {type}
            </option>
          ))}
        </select>
      </div>
      <div className="col-12 col-md-4 col-xl-2">
        <label>Sort By: </label>
        <select
          className="form-select"
          onChange={(e) => {
            const value = e.target.value;
            setPriceSort(null);
            setHorsepowerSort(null);
            setBuildYearSort(null);
            switch (value) {
              case 'priceAsc':
                setPriceSort('ASC');
                break;
              case 'priceDesc':
                setPriceSort('DESC');
                break;
              case 'horsepowerAsc':
                setHorsepowerSort('ASC');
                break;
              case 'horsepowerDesc':
                setHorsepowerSort('DESC');
                break;
              case 'buildYearAsc':
                setBuildYearSort('ASC');
                break;
              case 'buildYearDesc':
                setBuildYearSort('DESC');
                break;
              default:
                break;
            }
          }}
        >
          <option value="">None</option>
          <option value="priceAsc">Price &#8593;</option>
          <option value="priceDesc">Price &#8595;</option>
          <option value="horsepowerAsc">Horsepower &#8593;</option>
          <option value="horsepowerDesc">Horsepower &#8595;</option>
          <option value="buildYearAsc">Build Year &#8593;</option>
          <option value="buildYearDesc">Build Year &#8595;</option>
        </select>
      </div>
      <div className="col-12">
        <button
          className="btn btn-primary w-100 "
          onClick={() => {
            updateQueryString();
          }}
        >
          Search
        </button>
      </div>
    </div>
  );
}
