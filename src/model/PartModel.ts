import { BrandModel } from "./BrandModel"
import { KitPartModel } from "./KitPartModel"
import { OENumberModel } from "./OENumberModel"

export class PartModel {
    id: number
    title: string
    url: string
    sku: string
    fcpEuroId: number
    quantity: number
    price: number
    quality: string
    mfgNumbers: string[]
    kit: boolean
    madeIn: string
    categoryBot: string
    img: string[]
    brand: BrandModel
    oeNumbers: string[]
    kitParts: KitPartModel[]
    productInformationHtml: string

    constructor(
        id: number,
        title: string,
        url: string,
        sku: string,
        fcpEuroId: number,
        quantity: number,
        price: number,
        quality: string,
        mfgNumbers: string[],
        kit: boolean,
        madeIn: string,
        categoryBot: string,
        img: string[],
        brand: BrandModel,
        oeNumbers: string[],
        kitParts: KitPartModel[],
        productInformationHtml: string
    ) {
        this.id = id
        this.title = title
        this.url = url
        this.sku = sku
        this.fcpEuroId = fcpEuroId
        this.quantity = quantity
        this.price = price
        this.quality = quality
        this.mfgNumbers = mfgNumbers
        this.kit = kit
        this.madeIn = madeIn
        this.categoryBot = categoryBot
        this.img = img
        this.brand = brand
        this.oeNumbers = oeNumbers
        this.kitParts = kitParts
        this.productInformationHtml = productInformationHtml
    }
}