import {Outlet} from "react-router-dom";
import Header from "./header/Header.tsx";
import Footer from "./Footer.tsx";

export default function Layout() {
    return (
        <>
            <Header />
            <main>
                <Outlet />
            </main>
            <Footer />
        </>
    )
}