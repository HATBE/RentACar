import HeaderNavLinkButton from './HeaderNavLinkButton.tsx';
import { Link } from 'react-router-dom';

import './header.css';

export default function Header() {
  return (
    <header>
      <nav className="navbar navbar-expand-lg bg-dark navbar-dark">
        <div className="container">
          <Link className="navbar-brand link-light text-decoration-none" to="/">
            <h1 className="h3 text-light fw-bold">
              Rent<i className="cyan-text-gradient">A</i>Car.ch
            </h1>
          </Link>
          <div className="d-flex justify-content-end">
            <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarHeader"
              aria-controls="navbarHeader"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
          </div>
          <div className="collapse navbar-collapse" id="navbarHeader">
            <ul className="ms-3 navbar-nav me-auto">
              <HeaderNavLinkButton link={'/admin'} text={'ADMIN'} />
            </ul>
          </div>
        </div>
      </nav>
    </header>
  );
}
