import CarListItem from './CarListItem.tsx';

export default function CarList() {
  return (
    <div>
      <div>FILTERBAR</div>
      <div className="row g-3">
        {Array.from(Array(12), () => {
          return <CarListItem />;
        })}
      </div>
    </div>
  );
}
