import BookingCalendar from "../../../../booking/bookingCalendar/BookingCalendar.tsx";

type DateRangePickerFilterProps = {
    setStartDate: (startDate: Date) => void;
    setEndDate: (endDate: Date) => void;
    startDate: Date | null;
    endDate: Date | null;
}

export default function DateRangePickerFilterInput({setStartDate, setEndDate, startDate, endDate}: DateRangePickerFilterProps) {
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
                    <BookingCalendar disableDates={false} selectDatesCallback={selectDatesCallback}/>
                </li>
            </ul>
        </div>
    )
}