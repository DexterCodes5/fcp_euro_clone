export class DeliveryModel {
    receiveTextNotification: boolean
    price: number
    receiveDate: Date

    constructor(receiveTextNotification: boolean, price: number, receiveDate: Date) {
        this.receiveTextNotification = receiveTextNotification
        this.price = price
        this.receiveDate = receiveDate
    }
}