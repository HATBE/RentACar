import AdminCarList from '../../components/car/adminCarList/AdminCarList.tsx';
import { Link } from 'react-router-dom';
import AdminBookingList from '../../components/booking/adminBookingList/AdminBookingList.tsx';

export default function AdminLandingPage() {
  return (
    <>
      <h1>Admin </h1>

      <div className="card bg-dark  text-light border-0 shadow-lg overflow-hidden">
        <h4 className="card-header">Car list</h4>
        <div className="card-body">
          <Link to={'/admin/cars/create'} className="mb-2 btn btn-sm btn-primary">
            <i className="bi bi-plus-circle me-2"></i>Add Car
          </Link>
          <AdminCarList />

        </div>
      </div>

      <div className="card bg-dark mt-3 text-light border-0 shadow-lg overflow-hidden">
        <h4 className="card-header">Open bookings</h4>
        <div className="card-body">
          <AdminBookingList />
        </div>
      </div>
    </>
  );
}
