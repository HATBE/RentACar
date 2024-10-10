import { Link } from 'react-router-dom';

export default function CarListItem() {
  return (
    <div className="col-12 col-md-4 col-xl-3">
      <Link className="car-item link-dark text-decoration-none" to="/cars/1">
        <div className="card overflow-hidden">
          <div className="card-body p-0">
            <img className="user-select-none thumpnail" src="/assets/img/car.jpg" alt="" />
            <div className="p-3">
              <div className="text-center h5 fw-bold">Nissan Skyline R34 GT-R 1998</div>
              <div className="row g-2 mt-2">
                <div className="col-6">
                  <div className="bg-dark rounded text-light px-2 py-1">
                    <i className="bi bi-speedometer me-2"></i>
                    <span>280 PS</span>
                  </div>
                </div>
                <div className="col-6 ">
                  <div className="bg-dark rounded text-light px-2 py-1">
                    <i className="bi bi-person-fill me-2"></i>
                    <span>4</span>
                  </div>
                </div>
                <div className="col-6 ">
                  <div className="bg-dark rounded text-light px-2 py-1">
                    <i className="bi bi-arrow-down-up me-2"></i>
                    <span>Manual</span>
                  </div>
                </div>
                <div className="col-6">
                  <div className="bg-dark rounded text-light px-2 py-1">
                    <i className="bi bi-fuel-pump me-2"></i>
                    <span>GASOLINE</span>
                  </div>
                </div>
              </div>
              <div className="mt-4 mb-2 h4 fw-bold cyan-text-gradient text-center">
                CHF 500.- / Tag
              </div>
            </div>
          </div>
        </div>
      </Link>
    </div>
  );
}
