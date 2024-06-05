import "./Fitment.css"
import { FitmentModel } from "../../../../model/fitment/FitmentModel"

export const Fitment: React.FC<{
    fitments: FitmentModel[], partCategory: string, fitmentTab: React.RefObject<HTMLDivElement>
}> = ({ fitments, partCategory, fitmentTab }) => {
    
    return (
        <div className="tabs-panel" ref={fitmentTab}>
            <h2>Fits These cars</h2>
            <div className="fitmentGuide__models">
                {fitments.map(fitment =>
                    <div className="fitmentGuide__applicationGroup" key={fitment.model}>
                        <h4>{fitment.make} {fitment.model}</h4>
                        <ul>
                            {fitment.vehicles.map(fitmentVehicle =>
                                <li key={fitmentVehicle.id}>
                                    <div>
                                        {fitmentVehicle.year} {fitmentVehicle.make} {fitmentVehicle.model} {partCategory}
                                    </div>
                                    <strong>{fitmentVehicle.comment}</strong>
                                </li>
                            )}
                        </ul>
                    </div>
                )}
            </div>
        </div>
    )
}