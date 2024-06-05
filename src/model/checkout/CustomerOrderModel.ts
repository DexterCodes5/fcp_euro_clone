import { AddressModel } from "./AddressModel";
import { CartItemModel } from "../CartItemModel";
import { DeliveryModel } from "./DeliveryModel";

export class CustomerOrderModel {
    email: string
    cartItems: CartItemModel[]
    address: AddressModel
    delivery: DeliveryModel
    payment: string
    subtotal: number
    shipping: number
    tax: number
    total: number

    constructor(email: string,
                cartItems: CartItemModel[],
                address: AddressModel,
                delivery: DeliveryModel,
                payment: string,
                subtotal: number,
                shipping: number,
                tax: number,
                total: number) {
        this.email = email
        this.cartItems = cartItems
        this.address = address
        this.delivery = delivery
        this.payment = payment
        this.subtotal = subtotal
        this.shipping = shipping
        this.tax = tax
        this.total = total
    }
}