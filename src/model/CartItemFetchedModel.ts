import { PartModel } from "./PartModel"

export class CartItemFetchedModel {
    part: PartModel
    qty: number

    constructor(part: PartModel, qty: number) {
        this.part = part
        this.qty = qty
    }
}