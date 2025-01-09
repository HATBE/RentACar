import { Booking } from '../../../types/Booking.ts';
import { Link } from 'react-router-dom';

type AdminBookingListItemProps = {
  booking: Booking;
};

export default function AdminBookingListItem({ booking }: AdminBookingListItemProps) {
  return (
    <tr>
      <td>{booking.id}</td>
      <td className="text-nowrap">
        {new Date(booking.startDate).toLocaleDateString()} -{' '}
        {new Date(booking.endDate).toLocaleDateString()}
      </td>
      <td className="text-nowrap">
        <Link className="text-decoration-none link-light" to={`/admin/cars/${booking.car.id}`}>
          {booking.car.make} {booking.car.model} {booking.car.buildYear}
        </Link>
      </td>
      <td className="w-100">{booking.customerName}</td>
    </tr>
  );
}