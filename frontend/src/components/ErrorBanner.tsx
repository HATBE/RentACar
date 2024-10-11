type ErrorBannerProps = {
  message: string;
};

export default function ErrorBanner({ message }: ErrorBannerProps) {
  return (
    <div className="alert alert-danger w-100 m-0">
      <i className="bi bi-danger"></i>
      <span className="ms-2 fw-bold">Error!</span>
      <span className="ms-2">{message}</span>
    </div>
  );
}
