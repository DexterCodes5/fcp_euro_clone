import { useEffect, useRef, useState } from "react"
import axios from "axios"
import "./VehicleDropdowns.css"
import { MakeModel } from "../../../../model/vehicle/MakeModel"
import { BaseVehicleModel } from "../../../../model/vehicle/BaseVehicleModel"
import { VehicleModel } from "../../../../model/vehicle/VehicleModel"
import { BodyModel } from "../../../../model/vehicle/BodyModel"
import { EngineModel } from "../../../../model/vehicle/EngineModel"
import { TransmissionModel } from "../../../../model/vehicle/TransmissionModel"
import { SavedVehicleModel } from "../../../../model/SavedVehicleModel"

export const VehicleDropdowns = () => {
    const [years, setYears] = useState<number[]>([])
    const [makes, setMakes] = useState<MakeModel[]>([])
    const [models, setModels] = useState<BaseVehicleModel[]>([])
    const [subModels, setSubModels] = useState<VehicleModel[]>([])
    const [bodies, setBodies] = useState<BodyModel[]>([])
    const [engines, setEngines] = useState<EngineModel[]>([])
    const [transmissions, setTransmissions] = useState<TransmissionModel[]>([])

    const [isMakeDropdownDisabled, setIsMakeDropdownDisabled] = useState(true)
    const [isModelDropdownDisabled, setIsModelDropdownDisabled] = useState(true)

    const [showSubModelDropdown, setShowSubModelDropdown] = useState(false)
    const [showBodyDropdown, setShowBodyDropdown] = useState(false)
    const [showEngineDropdown, setShowEngineDropdown] = useState(false)
    const [showTransmissionDropdown, setShowTransmissionDropdown] = useState(false)

    const [year, setYear] = useState("Year")
    const [make, setMake] = useState("Make")
    const [model, setModel] = useState("Model")
    const [subModel, setSubModel] = useState("Sub model")
    const [body, setBody] = useState("Body")
    const [engine, setEngine] = useState("Engine")
    const [transmission, setTransmission] = useState("Transmission")

    const [triggerGetModels, setTriggerGetModels] = useState(false)
    const [triggerGetSubModels, setTriggerGetSubModels] = useState(false)
    const [triggerGetBodies, setTriggerGetBodies] = useState(false)
    const [triggerGetEngines, setTriggerGetEngines] = useState(false)
    const [triggerGetTransmissions, setTriggerGetTransmissions] = useState(false)

    const vehicleForSaving = useRef(new SavedVehicleModel())

    useEffect(() => {
        const getYears = async () => {
            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/vehicles/years`)
            setYears(res.data)
        }

        getYears()
    }, [])

    useEffect(() => {
        const getMakes = async () => {
            if (year === "Year")
                return

            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/vehicles/makes?year=${year}`)
            setMakes(res.data)
        }

        getMakes()
    }, [year])

    useEffect(() => {
        const getModels = async () => {
            if (make === "Make")
                return

            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/vehicles/base_vehicles?year=${year}&make=${make}`)
            setModels(res.data)
            setTriggerGetSubModels(prevVal => !prevVal)
        }

        getModels()
    }, [triggerGetModels])

    useEffect(() => {
        const getSubModels = async () => {
            if (model === "Model")
                return
            
            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/vehicles/vehicles?baseVehicleId=${model}`)
            setSubModels(res.data)
            if (res.data.length === 1) {
                vehicleForSaving.current.vehicle = res.data[0]
                setSubModel(res.data[0].id)
                setShowBodyDropdown(true)
                setTriggerGetBodies(prevVal => !prevVal)
            }
        }

        getSubModels()
    }, [triggerGetSubModels])

    useEffect(() => {
        const getBodies = async () => {
            if (subModel === "Sub model")
                return

            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/vehicles/bodies?vehicleId=${subModel}`)
            setBodies(res.data)
            if (res.data.length === 1) {
                vehicleForSaving.current.body = res.data[0]
                setBody(res.data[0].id)
                setShowEngineDropdown(true)
                setTriggerGetEngines(prevVal => !prevVal)
            }
        }

        getBodies()
    }, [triggerGetBodies])

    useEffect(() => {
        const getEngines = async () => {
            if (body === "Body")
                return

            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/vehicles/engines?vehicleId=${subModel}&bodyId=${body}`)
            setEngines(res.data)
            if (res.data.length === 1) {
                vehicleForSaving.current.engine = res.data[0]
                setEngine(res.data[0].id)
                setShowTransmissionDropdown(true)
                setTriggerGetTransmissions(prevVal => !prevVal)
            }
        }

        getEngines()
    }, [triggerGetEngines])

    useEffect(() => {
        const getTransmissions = async () => {
            if (engine === "Engine")
                return

            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/vehicles/transmissions?vehicleId=${subModel}&bodyId=${body}&engineId=${engine}`)
            setTransmissions(res.data)
        }

        getTransmissions()
    }, [triggerGetTransmissions])

    const changeYear = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setYear(e.target.value)
        if (e.target.value !== "Year") {
            setIsMakeDropdownDisabled(false)
        }
    }

    const changeMake = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setMake(e.target.value)
        if (e.target.value !== "Make") {
            vehicleForSaving.current.make = makes.find(m => m.id === Number(e.target.value))
            setIsModelDropdownDisabled(false)
            setTriggerGetModels(prevVal => !prevVal)
        }
    }

    const changeModel = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setModel(e.target.value)
        if (e.target.value !== "Model") {
            vehicleForSaving.current.baseVehicle = models.find(m => m.id === Number(e.target.value))
            setShowSubModelDropdown(true)
            setTriggerGetSubModels(prevVal => !prevVal)
        }
    }

    const changeSubModel = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSubModel(e.target.value)
        if (e.target.value !== "Sub Model") {
            vehicleForSaving.current.vehicle = subModels.find(sm => sm.id === Number(e.target.value))
            setShowBodyDropdown(true)
            setTriggerGetBodies(prevVal => !prevVal)
        }
    }

    const changeBody = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setBody(e.target.value)
        if (e.target.value !== "Body") {
            vehicleForSaving.current.body = bodies.find(b => b.id === Number(e.target.value))
            setShowEngineDropdown(true)
            setTriggerGetEngines(prevVal => !prevVal)
        }
    }

    const changeEngine = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setEngine(e.target.value)
        if (e.target.value !== "Engine") {
            vehicleForSaving.current.engine = engines.find(eng => eng.id === Number(e.target.value))
            setShowTransmissionDropdown(true)
            setTriggerGetTransmissions(prevVal => !prevVal)
        }
    }

    const changeTransmission = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setTransmission(e.target.value)

        vehicleForSaving.current.transmission = transmissions.find(t => t.id == e.target.value)
        const savedVehicles = JSON.parse(localStorage.getItem("saved-vehicles")!)
        savedVehicles.unshift(vehicleForSaving.current)
        localStorage.setItem("saved-vehicles", JSON.stringify(savedVehicles))

        window.location.href = `${process.env.REACT_APP_URL}/${vehicleForSaving.current.make?.make}-parts/${vehicleForSaving.current.baseVehicle?.model}?year=${vehicleForSaving.current.baseVehicle?.year}&v=${subModel}&b=${body}&e=${engine}&t=${e.target.value}`
    }

    return (
        <div className="vehicle-dropdowns">
            <select name="yearDropdown" className="vehicleSelector__select" value={year} 
            onChange={changeYear}>
                <option>Year</option>
                {years.map(year =>
                    <option value={year} key={year}>{year}</option>
                )}
            </select>
            <select name="makeDropdown" className="vehicleSelector__select" 
            disabled={isMakeDropdownDisabled} value={make} onChange={changeMake}>
                <option>Make</option>
                {makes?.map(make =>
                    <option value={make.id} key={make.id}>{make.make}</option>
                )}
            </select>
            <select name="modelDropdown" className="vehicleSelector__select" 
            disabled={isModelDropdownDisabled} value={model} onChange={changeModel}>
                <option>Model</option>
                {models?.map(model =>
                    <option value={model.id} key={model.id}>{model.model}</option>
                )}
            </select>
            {showSubModelDropdown &&
                <select name="subModelDropdown" className="vehicleSelector__select" value={subModel} onChange={changeSubModel}>
                    <option>Sub model</option>
                    {subModels?.map(subModel =>
                        <option value={subModel.id} key={subModel.id}>{subModel.subModel}</option>
                    )}
                </select>
            }
            {showBodyDropdown &&
                <select name="bodyDropdown" className="vehicleSelector__select" value={body}
                onChange={changeBody}>
                    <option>Body</option>
                    {bodies?.map(body =>
                        <option value={body.id} key={body.id}>{body.body}</option>
                    )}
                </select>
            }
            {showEngineDropdown &&
                <select name="engineDropdown" className="vehicleSelector__select" value={engine}
                    onChange={changeEngine}>
                    <option>Engine</option>
                    {engines?.map(e =>
                        <option value={e.id} key={e.id}>{e.engine}</option>
                    )}
                </select>
            }
            {showTransmissionDropdown &&
                <select name="transmissionDropdown" className="vehicleSelector__select" 
                value={transmission} onChange={changeTransmission}>
                    <option>Transmission</option>
                    {transmissions?.map(t =>
                        <option value={t.id} key={t.id}>{t.transmission}</option>
                    )}
                </select>
            }
        </div>
    )
}