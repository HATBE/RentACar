import HeaderNavLinkButton from "./HeaderNavLinkButton.tsx";

export default function Header() {
  return (
    <header>
      <nav className="navbar navbar-expand-lg bg-dark navbar-dark">
        <div className="container">
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
            <h1 className="h3 text-light fw-bold">Carmania</h1>
            <ul className="ms-3 navbar-nav me-auto">
              <HeaderNavLinkButton link={"/"} text={"Home"} />
              <HeaderNavLinkButton link={"/cars/1"} text={"Car"} />
            </ul>
          </div>
        </div>
      </nav>
    </header>
  );
}
