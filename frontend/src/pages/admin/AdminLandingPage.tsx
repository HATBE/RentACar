import AdminCarList from '../../components/car/adminCarList/AdminCarList.tsx';
import { Link } from 'react-router-dom';

export default function AdminLandingPage() {
  return (
    <>
      <h1>Admin </h1>
      <div className="my-3">
        <Link to={'/cars/create'} className="btn btn-primary">
          <i className="bi bi-plus-circle me-2"></i>Add Car
        </Link>
      </div>
      <AdminCarList />
    </>
  );
}
