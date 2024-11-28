type ErrorBannerProps = {
  message: string;
};

export default function SuccessBanner({ message }: ErrorBannerProps) {
  return (
    <div className="alert alert-success w-100 m-0">
      <i className="bi bi-check-circle-fill"></i>
      <span className="ms-2 fw-bold">Error!</span>
      <span className="ms-2">{message}</span>
    </div>
  );
}
