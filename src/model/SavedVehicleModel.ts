import { BaseVehicleModel } from "./vehicle/BaseVehicleModel"
import { BodyModel } from "./vehicle/BodyModel"
import { EngineModel } from "./vehicle/EngineModel"
import { MakeModel } from "./vehicle/MakeModel"
import { TransmissionModel } from "./vehicle/TransmissionModel"
import { VehicleModel } from "./vehicle/VehicleModel"

export class SavedVehicleModel {
    make?: MakeModel
    baseVehicle?: BaseVehicleModel
    vehicle?: VehicleModel
    body?: BodyModel
    engine?: EngineModel
    transmission?: TransmissionModel

    constructor()
    constructor(
        make?: MakeModel,
        baseVehicle?: BaseVehicleModel,
        vehicle?: VehicleModel,
        body?: BodyModel,
        engine?: EngineModel,
        transmission?: TransmissionModel
    ) {
        this.make = make
        this.baseVehicle = baseVehicle
        this.vehicle = vehicle
        this.body = body
        this.engine = engine
        this.transmission = transmission
    }
}