import { useEffect, useState } from 'react';
import { Booking } from '../../../types/Booking.ts';
import DatePicker from 'react-datepicker';
import './bookingCalendar.css';
import 'react-datepicker/dist/react-datepicker.css';

type BookingProps = {
  bookings: Booking[];
  selectDatesCallback: (startDate: Date, endDate: Date) => void;
  onClearDates?: (clearFn: () => void) => void;
};

export default function BookingCalendar({
  bookings,
  selectDatesCallback,
  onClearDates,
}: BookingProps) {
  const [dateRange, setDateRange] = useState<[Date | null, Date | null]>([null, null]);

  const dateRanges = bookings.map((booking) => ({
    start: new Date(booking.startDate).setHours(0, 0, 0, 0), // 00:00:00
    end: new Date(booking.endDate).setHours(23, 59, 59, 999), // 23:59:59
  }));

  const isDateBooked = (date: Date) => {
    const day = date.setHours(0, 0, 0, 0); // 00:00:00
    return dateRanges.some(({ start, end }) => day >= start && day <= end);
  };

  const highlightWithRanges = (date: Date) => {
    return isDateBooked(date) ? 'booked-date' : undefined;
  };

  const startDate = dateRange[0] || undefined;
  const endDate = dateRange[1] || undefined;

  const handleDateChange = (update: Date | [Date | null, Date | null]) => {
    if (!Array.isArray(update)) {
      return;
    }
    setDateRange(update);

    if (update[0] && update[1]) {
      // here the daterange is selected and ok
      selectDatesCallback(update[0], update[1]);
    }
  };

  const clearDates = () => {
    setDateRange([null, null]);
  };

  useEffect(() => {
    if (onClearDates) {
      onClearDates(clearDates);
    }
  }, [onClearDates]);

  return (
    <div>
      <DatePicker
        onChange={handleDateChange}
        startDate={startDate}
        endDate={endDate}
        selectsRange
        inline
        dayClassName={(date) => highlightWithRanges(date) || ''}
      />
    </div>
  );
}
