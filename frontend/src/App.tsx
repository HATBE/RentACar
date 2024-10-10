import './App.css'
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Layout from "./components/Layout.tsx";
import LandingPage from "./pages/LandingPage.tsx";
import CarPage from "./pages/CarPage.tsx";
import NotFoundPage from "./pages/errors/NotFoundPage.tsx";

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<LandingPage />} />
                    <Route path="/car/:carid" element={<CarPage />} />
                    <Route path="*" element={<NotFoundPage />} />
                </Route>
            </Routes>
        </Router>
    )
}