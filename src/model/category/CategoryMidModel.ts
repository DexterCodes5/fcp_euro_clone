export class CategoryMidModel {
    categoryMid: string
    categoriesBot: string[]

    constructor(categoryMid: string, categoriesBot: string[]) {
        this.categoryMid = categoryMid
        this.categoriesBot = categoriesBot
    }
}