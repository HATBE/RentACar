import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CarService from '../../../../services/CarsApi.ts';
import CarCarCategoriesService from '../../../../services/CarCategoriesApi.ts';
import { CarCategory } from '../../../../types/CarCategory.ts';
import BookingsService from '../../../../services/BookingsService.ts';
import DateRangePickerFilterInput from "./filterInputs/DateRangePickerFilterInput.tsx";
import NumberFilterInput from "./filterInputs/NumberFilterInput.tsx";
import StringFilterInput from "./filterInputs/StringFilterInput.tsx";
import SelectFilterInput from "./filterInputs/SelectFilterInput.tsx";
import CategoryFilterInput from "./filterInputs/CategoryFilterInput.tsx";
import SortByFilterInput from "./filterInputs/SortByFilterInput.tsx";

interface FilterBarProps {
  defaultValues: URLSearchParams;
  onApplyFilters: () => void;
}

export default function CarListFilterBar({ defaultValues }: FilterBarProps) {
  const navigate = useNavigate();
  const [buildYearFrom, setBuildYearFrom] = useState<string | null>(defaultValues.get('buildYearFrom'));
  const [buildYearTo, setBuildYearTo] = useState<string | null>(defaultValues.get('buildYearTo'));
  const [make, setMake] = useState<string | null>(defaultValues.get('make'));
  const [category, setCategory] = useState<string | null>(defaultValues.get('category'));
  const [priceMin, setPriceMin] = useState<string | null>(defaultValues.get('priceMin'));
  const [priceMax, setPriceMax] = useState<string | null>(defaultValues.get('priceMax'));
  const [seatsMin, setSeatsMin] = useState<string | null>(defaultValues.get('seatsMin'));
  const [seatsMax, setSeatsMax] = useState<string | null>(defaultValues.get('seatsMax'));
  const [gearType, setGearType] = useState<string | null>(defaultValues.get('gearType'));
  const [fuelType, setFuelType] = useState<string | null>(defaultValues.get('fuelType'));
  const [startDate, setStartDate] = useState<Date | null>(defaultValues.get('startDate') ? new Date(defaultValues.get('startDate')!) : null);
  const [endDate, setEndDate] = useState<Date | null>(defaultValues.get('endDate') ? new Date(defaultValues.get('endDate')!) : null);

  const [priceSort, setPriceSort] = useState<string | null>(defaultValues.get('priceSort'));
  const [horsepowerSort, setHorsepowerSort] = useState<string | null>(defaultValues.get('horsepowerSort'));
  const [buildYearSort, setBuildYearSort] = useState<string | null>(defaultValues.get('buildYearSort'));

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

    if (startDate) {
      const normalizedStartDate = BookingsService.normalizeDate(startDate, 0, 0, 0);
      params.set('startDate', normalizedStartDate.toISOString().slice(0, 10));
    }

    if (endDate) {
      const normalizedEndDate = BookingsService.normalizeDate(endDate, 23, 59, 59);
      params.set('endDate', normalizedEndDate.toISOString().slice(0, 10));
    }

    navigate(`?${params.toString()}`, { replace: true });
  };

  const filters = [
    <NumberFilterInput title="Build Year From" setter={setBuildYearFrom} value={buildYearFrom} min="1900" max="2099"/>,
    <NumberFilterInput title="Build Year To" setter={setBuildYearTo} value={buildYearTo} min="1900" max="2099"/>,
    <StringFilterInput title="Make" setter={setMake} value={make} />,
    <CategoryFilterInput value={category} setter={setCategory} categories={categories}/>,
    <NumberFilterInput title="Price min" setter={setPriceMin} value={priceMin}/>,
    <NumberFilterInput title="Price max" setter={setPriceMax} value={priceMax}/>,
    <NumberFilterInput title="Seats min" setter={setSeatsMin} value={seatsMin}/>,
    <NumberFilterInput title="Seats max" setter={setSeatsMax} value={seatsMax}/>,
    <SelectFilterInput title="Gear Type" value={gearType} setter={setGearType} inputArray={gearTypes}/>,
    <SelectFilterInput title="Fuel Type" value={fuelType} setter={setFuelType} inputArray={fuelTypes}/>,
    <SortByFilterInput setBuildYearSort={setBuildYearSort} setHorsepowerSort={setHorsepowerSort} setPriceSort={setPriceSort}></SortByFilterInput>,
    <DateRangePickerFilterInput setStartDate={setStartDate} setEndDate={setEndDate} startDate={startDate} endDate={endDate} />
  ];

  return (
      <div className="row g-3 mb-3">
        <h4>Filter Cars</h4>

        {filters.map((filter, idx) => (
            <div key={idx} className="col-12 col-md-4 col-xl-2">
              {filter}
            </div>
        ))}

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