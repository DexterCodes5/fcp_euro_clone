export class AddressModel {
    firstName: string
    lastName: string
    streetAddress: string
    streetAddressContd?: string
    companyName?: string
    city: string
    country: string
    state: string
    zipCode: string
    phoneNumber: string

    constructor(
        firstName: string,
        lastName: string,
        streetAddress: string,
        city: string,
        country: string,
        state: string,
        zipCode: string,
        phoneNumber: string,
        streetAddressContd?: string,
        companyName?: string
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.streetAddress = streetAddress
        this.city = city
        this.country = country
        this.state = state
        this.zipCode = zipCode
        this.phoneNumber = phoneNumber
        this.streetAddressContd = streetAddressContd
        this.companyName = companyName
    }
}