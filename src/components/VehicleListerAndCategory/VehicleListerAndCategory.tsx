import { useLocalStorage } from "../../hooks/useLocalStorage"
import { Category } from "../Category/Category"
import { VehicleLister } from "../VehicleLister/VehicleLister"
import { SavedVehicleModel } from "../../model/SavedVehicleModel"

export const VehicleListerAndCategory: React.FC<{
    setRenderPartsPage: React.Dispatch<React.SetStateAction<boolean>> | null
}> = (props) => {
    const [savedVehicles] = useLocalStorage<SavedVehicleModel[]>("saved-vehicles", [])
    
    const renderCategory = () => {
        if (window.location.origin + window.location.pathname !== process.env.REACT_APP_URL + "/" 
        || savedVehicles.length > 0) {
            return <Category setRenderPartsPage={props.setRenderPartsPage} />
        }
    }
    
    return (
        <div>
            <VehicleLister />
            {renderCategory()}
        </div>
    )
}