import './App.css'
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Layout from "./components/Layout.tsx";
import LandingPage from "./pages/LandingPage.tsx";
import CarPage from "./pages/CarPage.tsx";
import NotFoundPage from "./pages/errors/NotFoundPage.tsx";

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<LandingPage />} />
                    <Route path="/car/:id" element={<CarPage />} />
                    <Route path="*" element={<NotFoundPage />} />
                </Route>
            </Routes>
        </Router>
    )
}