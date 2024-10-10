import {Link} from "react-router-dom";

export default function  Header() {
    return (
        <header>
            <div>
                header
            </div>
            <nav>
                <Link to="/">Home</Link>
                <Link to="/car/1">Car</Link>
            </nav>
        </header>
    );
};