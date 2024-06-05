export class FitmentVehicleModel {
    id: number
    year: number
    make: string
    model: string
    comment: string

    constructor(id: number, year: number, make: string, model: string, comment: string) {
        this.id = id
        this.year = year
        this.make = make
        this.model = model
        this.comment = comment
    }
}