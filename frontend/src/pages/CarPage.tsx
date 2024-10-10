import {useParams} from "react-router-dom";

export default function CarPage() {
    const { id } = useParams();

    return (
        <div>
            CAR ID: {id}
        </div>

    );
}