export const createPartsPageUrl = (make: string, model: string, category: string | undefined, 
    year: string, v: string, b: string, e: string, t: string, quality: string | null | undefined, 
    brand: string | null | undefined, searchOnly: string | null | undefined, order: string | null) => {
    let url = `${process.env.REACT_APP_URL}/${make}/${model}`
    if (category) {
        url += `/${category}`
    }
    url += `?year=${year}&v=${v}&b=${b}&e=${e}&t=${t}`
    if (quality && quality !== "All") {
        url += `&quality=${quality}`
    }
    if (brand && brand !== "All") {
        url += `&brand=${brand}`
    }
    if (searchOnly) {
        url += `&search_only=${searchOnly}`
    }
    if (order) {
        url += `&order=${order}`
    }
    return url
}