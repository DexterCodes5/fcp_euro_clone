export class ReviewModel {
    id?: number
    partId: number
    userId?: number
    createdAt: string
    name: string
    rating: number
    title: string
    text: string

    constructor(partId: number,
                createdAt: string,
                name: string,
                rating: number,
                title: string,
                text: string,
                id?: number,
                userId?: number) {
        this.partId = partId
        this.createdAt = createdAt
        this.name = name
        this.rating = rating
        this.title = title
        this.text = text
        this.id = id
        this.userId = userId
    }
}