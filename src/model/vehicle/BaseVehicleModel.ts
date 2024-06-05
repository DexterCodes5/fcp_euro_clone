export class BaseVehicleModel {
    id: number
    year: number
    makeId: number
    model: string

    constructor(
                id: number,
                year: number,
                makeId: number,
                model: string) {
        this.id = id
        this.year = year
        this.makeId = makeId
        this.model = model
    }
}