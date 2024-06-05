export class TransmissionModel {
    id: number | string
    transmission: string

    constructor(id: number | string, transmission: string) {
        this.id = id
        this.transmission = transmission
    }
}