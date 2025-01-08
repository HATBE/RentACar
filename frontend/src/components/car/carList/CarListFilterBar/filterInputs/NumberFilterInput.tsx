type NumberFilterInputProps = {
    title: string;
    setter: (value: string) => void;
    value: string | null;
    min?: string;
    max?: string;
    step?: string;
}

export default function NumberFilterInput({title, setter, value, step, min, max}: NumberFilterInputProps) {
    return (
        <>
            <label>{title}</label>
            <input
                placeholder="any"
                className="form-control"
                min={min}
                max={max}
                step={step}
                type="number"
                value={value || ''}
                onChange={(e) => setter(e.target.value)}
            />
        </>
    )
}