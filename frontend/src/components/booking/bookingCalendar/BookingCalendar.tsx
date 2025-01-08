import { useEffect, useState } from 'react';
import { Booking } from '../../../types/Booking.ts';
import DatePicker from 'react-datepicker';
import './bookingCalendar.css';
import 'react-datepicker/dist/react-datepicker.css';
import BookingsService from '../../../services/BookingsService.ts';

type BookingProps = {
  disableDates: boolean;
  selectDatesCallback?: (startDate: Date, endDate: Date, daysSelected: number) => void;
  onClearDates?: (clearCallback: () => void) => void;
  bookings?: Booking[];
};

export default function BookingCalendar({
  bookings,
  selectDatesCallback,
  onClearDates,
  disableDates,
}: BookingProps) {
  const [dateRange, setDateRange] = useState<[Date | null, Date | null]>([null, null]);

  const isDateBooked = (date: Date) => {
    if (!bookings) return false;
    const dateRanges = BookingsService.getDateRangesFromBookings(bookings);
    return BookingsService.isDateBooked(date, dateRanges);
  };

  const highlightRanges = (date: Date) => {
    return isDateBooked(date) ? 'booked-date' : undefined;
  };

  const handleDateChange = (dates: Date | [Date | null, Date | null]) => {
    if (!Array.isArray(dates)) {
      return;
    }

    setDateRange(dates);

    const startDate = dates[0];
    const endDate = dates[1];

    if (startDate && endDate) {
      const daysSelected = BookingsService.calculateDaysFromDateRange(startDate, endDate);
      if (selectDatesCallback) {
        selectDatesCallback(startDate, endDate, daysSelected);
      }
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

  const startDate = dateRange[0] || undefined;
  const endDate = dateRange[1] || undefined;

  return (
    <div>
      <DatePicker
        onChange={handleDateChange}
        startDate={startDate}
        endDate={endDate}
        selectsRange
        inline
        filterDate={() => !disableDates}
        dayClassName={(date) => highlightRanges(date) || ''}
      />
    </div>
  );
}
