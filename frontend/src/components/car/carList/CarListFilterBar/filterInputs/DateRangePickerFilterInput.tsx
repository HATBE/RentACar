import BookingCalendar from "../../../../booking/bookingCalendar/BookingCalendar.tsx";
import {useRef} from "react";

type DateRangePickerFilterProps = {
    setStartDate: (startDate: Date | null) => void;
    setEndDate: (endDate: Date | null) => void;
    startDate: Date | null;
    endDate: Date | null;
}

export default function DateRangePickerFilterInput({setStartDate, setEndDate, startDate, endDate}: DateRangePickerFilterProps) {
    const clearDatesCallback = useRef<() => void>();

    const dateRangeText = () => {
        if(!startDate && !endDate) {
            return 'None';
        }
        return `${startDate?.toLocaleDateString()} - ${endDate?.toLocaleDateString()}`;
    }

    const selectDatesCallback = (startDate: Date, endDate: Date) => {
        setStartDate(startDate);
        setEndDate(endDate);
    };

    const onClearDates = () => {
        setStartDate(null);
        setEndDate(null);
        if (clearDatesCallback.current) {
            clearDatesCallback.current();
        }
    }

    return (
        <div className="dropdown">
            <label>Date range</label>
            <button
                className="btn w-100 btn-light dropdown-toggle text-start"
                type="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
                data-bs-auto-close="outside"
            >
                {dateRangeText()}
            </button>
            <ul className="dropdown-menu p-0 overflow-hidden">
                <li>
                    <BookingCalendar onClearDates={(clearCallback) => (clearDatesCallback.current = clearCallback)} disableDates={false} selectDatesCallback={selectDatesCallback}/>
                    <button onClick={onClearDates} className="btn btn-danger btn-sm w-100 rounded-0"><i className="bi bi-x-circle-fill"></i> Clear</button>
                </li>
            </ul>
        </div>
    )
}