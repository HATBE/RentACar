type ButtonProps = {
  text: string;
  biIcon?: string;
  biColor?: string;
};

export default function Button({ text, biIcon, biColor }: ButtonProps) {
  const color = biColor ? biColor : 'primary';

  return (
    <button className={`btn btn-${color}`}>
      {biIcon && <i className={`bi bi-${biIcon} me-2`}></i>}
      <span>{text}</span>
    </button>
  );
}
