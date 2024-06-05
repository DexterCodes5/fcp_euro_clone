import { useLocalStorage } from "../../hooks/useLocalStorage"
import "./VehicleLister.css"
import { SavedVehicleModel } from "../../model/SavedVehicleModel"
import { useState } from "react"
import { VehicleDropdowns } from "./components/VehicleDropdowns/VehicleDropdowns"
import { Link } from "react-router-dom"

export const VehicleLister = () => {
    const [savedVehicles, setSavedVehicles] = useLocalStorage<SavedVehicleModel[]>("saved-vehicles", [])

    const [showVehicleDropdowns, setShowVehicleDropdowns] = useState(false)

    const removeCar = (savedVehicle: SavedVehicleModel) => {
        setSavedVehicles(savedVehicles.filter(sv => sv !== savedVehicle))
    }
    
    if (savedVehicles.length > 0) {
        return (
            <div className="vehicle-lister">
                <p className="vehicle-lister-heading vehicle-lister-heading-my-garage">My Garage</p>
                <ul className="saved-cars">
                    {savedVehicles.map(sv =>
                        <li key={sv.transmission?.id} className="saved-car-li">
                            <a href={`${process.env.REACT_APP_URL}/${sv.make?.make}-parts/${sv.baseVehicle?.model}?year=${sv.baseVehicle?.year}&v=${sv.vehicle?.id}&b=${sv.body?.id}&e=${sv.engine?.id}&t=${sv.transmission?.id}`} className="saved-car">
                                {sv.baseVehicle?.year} {sv.make?.make} {sv.baseVehicle?.model}
                            </a>
                            <a className="remove-car" onClick={() => removeCar(sv)}>&#x00d7;</a>
                        </li>
                    )}
                </ul>
                {!showVehicleDropdowns &&
                    <>
                        <a className="add-car-plus" onClick={() => setShowVehicleDropdowns(true)}>+</a>
                        <a className="add-car" onClick={() => setShowVehicleDropdowns(true)}>Add a car</a>
                    </>
                }
                {showVehicleDropdowns &&
                    <VehicleDropdowns />
                }
            </div>
        )
    } else {
        return (
            <div className="vehicle-lister">
                <p className="vehicle-lister-heading">Select you vehicle</p>
                <VehicleDropdowns />
            </div>
        )
    }
}