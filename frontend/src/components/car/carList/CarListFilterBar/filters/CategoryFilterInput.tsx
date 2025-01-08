import {CarCategory} from "../../../../../types/CarCategory.ts";

type CategoryFilterInput = {
    setter: (value: string) => void;
    categories: CarCategory[];
    value: string | null;
}

export default function CategoryFilterInput({setter, value, categories}: CategoryFilterInput) {
    return (
        <>
            <label>Category</label>
            <select
                className="form-select"
                value={value || ''}
                onChange={(e) => setter(e.target.value)}
            >
                <option value="">any</option>
                {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                        {category.name}
                    </option>
                ))}
            </select>
        </>
    )
}