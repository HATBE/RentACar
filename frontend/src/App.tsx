import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Layout from './components/layout/Layout.tsx';
import LandingPage from './pages/LandingPage.tsx';
import CarPage from './pages/CarPage.tsx';
import NotFoundPage from './pages/errors/NotFoundPage.tsx';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';

export default function App() {
  return (
    <Router>
      <Routes>
        :id
        <Route path="/" element={<Layout />}>
          <Route index element={<LandingPage />} />
          <Route path="/cars/:carid" element={<CarPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Route>
      </Routes>
    </Router>
  );
}
