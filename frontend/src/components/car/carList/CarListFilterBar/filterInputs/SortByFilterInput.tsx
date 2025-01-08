type SortByFilterInputProps = {
    setPriceSort: (value: string | null) => void;
    setHorsepowerSort: (value: string | null) => void;
    setBuildYearSort: (value: string | null) => void;
}

export default function SortByFilterInput({setPriceSort, setHorsepowerSort, setBuildYearSort}: SortByFilterInputProps) {
    return (
        <>
            <label>Sort By</label>
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
        </>
    )
}