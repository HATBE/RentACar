export default function LoadingSpinner() {
  return (
    <div className="w-100 d-flex justify-content-center">
      <div className="spinner-border" role="status">
        <span className="visually-hidden">Loading...</span>
      </div>
    </div>
  );
}
