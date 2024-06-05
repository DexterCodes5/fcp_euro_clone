export class VehicleModel {
    id: number
    baseVehicleId: number
    subModel: string

    constructor(id: number, baseVehicleId: number, subModel: string) {
        this.id = id
        this.baseVehicleId = baseVehicleId
        this.subModel = subModel
    }
}