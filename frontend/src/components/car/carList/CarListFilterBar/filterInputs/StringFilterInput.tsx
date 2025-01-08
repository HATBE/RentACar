type StringFilterInputProps = {
    title: string;
    setter: (value: string) => void;
    value: string | null;
}

export default function StringFilterInput({title, setter, value}: StringFilterInputProps) {
    return (
        <>
            <label>{title}</label>
            <input
                placeholder="any"
                className="form-control"
                type="text"
                value={value || ''}
                onChange={(e) => setter(e.target.value)}
            />
        </>
    )
}