import type { Booking } from '../../types/Booking.ts';

type BookingProps = {
  bookings: Booking[];
};

export default function BookingCalendar({ bookings }: BookingProps) {
  return (
    <div>
      Gebucht an folgenden Tagen:
      {bookings.map((booking) => (
        <div key={booking.id}>
          {
            <div>
              - {new Date(booking.startDate).toLocaleDateString()} -{' '}
              {new Date(booking.endDate).toLocaleDateString()}
            </div>
          }
        </div>
      ))}
    </div>
  );
}
