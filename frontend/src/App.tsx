import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Layout from './components/layout/Layout.tsx';
import LandingPage from './pages/LandingPage.tsx';
import CarPage from './pages/CarPage.tsx';
import NotFoundPage from './pages/errors/NotFoundPage.tsx';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';
import BookedCarPage from './pages/BookedCarPage.tsx';
import AdminLandingPage from './pages/admin/AdminLandingPage.tsx';
import CreateCarPage from './pages/admin/CreateCarPage.tsx';

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<LandingPage />} />
          <Route path="/cars/:carId" element={<CarPage />} />
          <Route path="/booked/:bookingId" element={<BookedCarPage />} />
          <Route path="/admin" element={<AdminLandingPage />} />
          <Route path="/admin/cat/create" element={<CreateCarPage />} />
          <Route path="/admin/car/edit/:carId" element={<CreateCarPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Route>
      </Routes>
    </Router>
  );
}
