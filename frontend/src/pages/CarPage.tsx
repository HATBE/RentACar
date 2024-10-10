import {useParams} from "react-router-dom";

export default function CarPage() {
    const { carid } = useParams();

    return (
        <div>
            CAR ID: {carid}

        </div>

    );
}