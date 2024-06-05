import { CategoryMidModel } from "./CategoryMidModel"

export class CategoryTopModel {
    categoryTop: string
    categoriesMid: CategoryMidModel[]

    constructor(categoryTop: string, categoriesMid: CategoryMidModel[]) {
        this.categoryTop = categoryTop
        this.categoriesMid = categoriesMid
    }
}