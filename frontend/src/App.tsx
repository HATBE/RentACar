import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Layout from './components/layout/Layout.tsx';
import LandingPage from './pages/LandingPage.tsx';
import CarPage from './pages/CarPage.tsx';
import NotFoundPage from './pages/errors/NotFoundPage.tsx';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';
import CarBookedPage from './pages/CarBookedPage.tsx';
import AdminLandingPage from './pages/admin/AdminLandingPage.tsx';
import AdminCarDetailPage from './pages/admin/AdminCarDetailPage.tsx';
import AdminCreateCarPage from './pages/admin/AdminCreateCarPage.tsx';
import AdminEditCarPage from './pages/admin/AdminEditCarPage.tsx';

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<LandingPage />} />
          <Route path="/cars/:carId" element={<CarPage />} />
          <Route path="/cars/booked/:bookingId" element={<CarBookedPage />} />
          <Route path="/admin" element={<AdminLandingPage />} />
          <Route path="/admin/cars/create" element={<AdminCreateCarPage />} />
          <Route path="/admin/cars/:carId" element={<AdminCarDetailPage />} />
          <Route path="/admin/cars/:carId/edit" element={<AdminEditCarPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Route>
      </Routes>
    </Router>
  );
}
