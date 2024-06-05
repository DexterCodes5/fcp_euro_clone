import { FitmentVehicleModel } from "./FitmentVehicleModel"

export class FitmentModel {
    make: string
    model: string
    vehicles: FitmentVehicleModel[]

    constructor(make: string, model: string, vehicles: FitmentVehicleModel[]) {
        this.make = make
        this.model = model
        this.vehicles = vehicles
    }
}