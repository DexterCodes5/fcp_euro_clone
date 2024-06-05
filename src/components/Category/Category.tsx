import { Link, useParams, useSearchParams } from "react-router-dom"
import { PartModel } from "../../model/PartModel"
import "./Category.css"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faCaretDown } from "@fortawesome/free-solid-svg-icons"
import { useEffect, useState } from "react"
import { useWindowDimensions } from "../../hooks/useWindowDimensions"
import { AnimatePresence, motion } from "framer-motion"
import { useLocalStorage } from "../../hooks/useLocalStorage"
import { SavedVehicleModel } from "../../model/SavedVehicleModel"
import axios from "axios"
import { CategoryTopModel } from "../../model/category/CategoryTopModel"
import { CategoryMidModel } from "../../model/category/CategoryMidModel"
import { createPartsPageUrl } from "../../util/UrlUtils"

export const Category: React.FC<{
    setRenderPartsPage: React.Dispatch<React.SetStateAction<boolean>> | null
}> = (props) => {
    const params = useParams()
    const [searchParams] = useSearchParams()

    const windowDimensions = useWindowDimensions()

    const [savedVehicles, setSavedVehicles] = useLocalStorage<SavedVehicleModel[]>("saved-vehicles", [])
    const [categories, setCategories] = useState<CategoryTopModel[]>([])

    const selectedCategory = params.category?.replaceAll("-", " ").replaceAll("~", "-")

    const [showCategoriesMobile, setShowCategoriesMobile] = useState(false)

    const categoriesVars = {
        initial: {
            scaleY: 0
        },
        animate: {
            scaleY: 1
        },
        exit: {
            scaleY: 0
        }
    }

    useEffect(() => {
        const getCategories = async () => {
            let vehicleId = -1
            const v = Number(searchParams.get("v"))
            if (savedVehicles.length > 0) {
                vehicleId = savedVehicles[0].vehicle?.id!
            } else if (v && !isNaN(v)) {
                vehicleId = v
            } else {
                console.error("Something went wrong")
                return
            }

            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/categories?vehicleId=${vehicleId}`)
            setCategories(res.data)
        }

        getCategories().catch(err => console.error(err))
    }, [])

    const createUrl = (category: string) => {
        category = category.replaceAll("-", "~").replaceAll(" ", "-")
        if (savedVehicles.length > 0) {
            return createPartsPageUrl(savedVehicles[0].make!.make + "-parts",
                savedVehicles[0].baseVehicle?.model!, category,
                savedVehicles[0].baseVehicle?.year.toString()!,
                savedVehicles[0].vehicle?.id.toString()!, savedVehicles[0].body?.id.toString()!, savedVehicles[0].engine?.id.toString()!,
                savedVehicles[0].transmission?.id.toString()!, searchParams.get("quality"),
                searchParams.get("brand"), searchParams.get("search_only"), searchParams.get("order")
            )
        } else {
            return createPartsPageUrl(params.make!, params.model!, category, searchParams.get("year")!,
                searchParams.get("v")!, searchParams.get("b")!, searchParams.get("e")!, 
                searchParams.get("t")!, searchParams.get("quality"), searchParams.get("brand"),
                searchParams.get("search_only"), searchParams.get("order")
            )
        }
    }

    const renderPartsPage = () => {
        if (props.setRenderPartsPage !== null) {
            props.setRenderPartsPage(prevVal => !prevVal)
        }
    }

    const renderTopCategory = (categoryTop: CategoryTopModel) => {
        let categoryTopIsSelected = false

        if (categoryTop.categoryTop === selectedCategory) {
            categoryTopIsSelected = true
        }

        const categoryMid = categoryTop.categoriesMid.find(categoryMid => {
            if (categoryMid.categoryMid === selectedCategory) {
                return true
            }
            if (categoryMid.categoriesBot.find(categoryBot => categoryBot === selectedCategory)) {
                return true
            }
            return false
        })

        if (categoryMid) {
            categoryTopIsSelected = true
        }

        return (
            <li className="category-item" key={categoryTop.categoryTop}>
                <Link to={createUrl(categoryTop.categoryTop)} onClick={renderPartsPage}
                    className={categoryTopIsSelected ? "category-link-active" : "category-link"}>
                    &nbsp;{categoryTop.categoryTop}
                </Link>
                {categoryTopIsSelected &&
                    <ul className="middle-categories">
                        {categoryTop.categoriesMid.map(renderMidCategory)}
                    </ul>
                }
            </li>
        )
    }

    const renderMidCategory = (categoryMid: CategoryMidModel) => {
        let categoryMidIsSelected = false

        if (categoryMid.categoryMid === selectedCategory) {
            categoryMidIsSelected = true
        }

        const selectedBotCat = categoryMid.categoriesBot.find(categoryBot => categoryBot === selectedCategory)
        if (selectedBotCat) {
            categoryMidIsSelected = true
        }

        return (
            <li className="category-item" key={categoryMid.categoryMid}>
                <Link to={createUrl(categoryMid.categoryMid)} onClick={renderPartsPage}
                    className={categoryMidIsSelected ? "category-link-active" : "category-link"}>
                    &nbsp;{categoryMid.categoryMid}
                </Link>
                {categoryMidIsSelected &&
                    <ul className="bottom-categories">
                        {categoryMid.categoriesBot.map(categoryBot =>
                            <li key={categoryBot}>
                                <Link to={createUrl(categoryBot)} onClick={renderPartsPage}
                                    className={categoryBot === selectedCategory ? "category-link-bot-active" : "category-link-bot"}>
                                    &nbsp;{categoryBot}
                                </Link>
                            </li>
                        )}
                    </ul>
                }
            </li>
        )
    }

    const renderCategoriesUl = () => {
        if (windowDimensions.width > 1023) {
            return (
                <ul className="top-categories">
                    {categories.map(renderTopCategory)}
                </ul>
            )
        }
        return (
            <AnimatePresence>
                {showCategoriesMobile &&
                    <motion.ul variants={categoriesVars} initial="initial" animate="animate" exit="exit"
                        transition={{ duration: 0.1, ease: "easeInOut" }}
                        className="top-categories">
                        {categories.map(renderTopCategory)}
                    </motion.ul>
                }
            </AnimatePresence>
        )
    }

    return (
        <div className="category">
            <a className="category-toggle">Category</a>
            <a className="category-toggle-mobile" onClick={() => setShowCategoriesMobile(!showCategoriesMobile)}>Category
                <FontAwesomeIcon icon={faCaretDown} className={`category-mobile-caret${showCategoriesMobile ? " category-mobile-caret-active" : ""}`} />
            </a>
            {renderCategoriesUl()}
        </div>
    )
}