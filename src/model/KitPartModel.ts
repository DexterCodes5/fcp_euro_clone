import { PartModel } from "./PartModel"

export class KitPartModel {
    kitId: number
    part: PartModel
    quantity: number

    constructor(
        kitId: number,
        part: PartModel,
        quantity: number
    ) {
        this.kitId = kitId
        this.part = part
        this.quantity = quantity
    }
}