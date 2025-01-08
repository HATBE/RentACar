type SelectFilterInputProps = {
    title: string;
    setter: (value: string) => void;
    inputArray: string[];
    value: string | null;
}

export default function SelectFilterInput({title, setter, value, inputArray}: SelectFilterInputProps) {
    return (
        <>
            <label>{title}</label>
            <select
                className="form-select"
                value={value || ''}
                onChange={(e) => setter(e.target.value)}
            >
                <option value="">any</option>
                {inputArray.map((item) => (
                    <option key={item} value={item}>
                        {item}
                    </option>
                ))}
            </select>
        </>
    )
}