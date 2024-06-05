export class BrandModel {
    id: number
    name: string
    img: string
    aboutHtml: string

    constructor(
        id: number,
        name: string,
        img: string,
        aboutHtml: string
    ) {
        this.id = id
        this.name = name
        this.img = img
        this.aboutHtml = aboutHtml
    }
}