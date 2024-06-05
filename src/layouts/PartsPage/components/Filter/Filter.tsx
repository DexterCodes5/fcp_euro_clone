import { Link, useParams, useSearchParams } from "react-router-dom"
import { PartModel } from "../../../../model/PartModel"
import { useRef, useState } from "react"
import { AnimatePresence, motion } from "framer-motion"
import { create } from "domain"
import { createPartsPageUrl } from "../../../../util/UrlUtils"
import { useClickOutside } from "../../../../hooks/useClickOutside"

export const Filter: React.FC<{
    parts: PartModel[], filterSort: string, setFilterSort: React.Dispatch<React.SetStateAction<string>>,
    filterRef: React.RefObject<HTMLDivElement>
}> = (props) => {
    const params = useParams()
    const [searchParams] = useSearchParams()

    const [selectionList, setSelectionList] = useState("")

    const selectedQuality: string = searchParams.get("quality") ? searchParams.get("quality")! : "All"

    let qualities = props.parts.map(part => part.quality)
    qualities = qualities.filter((quality, index) => qualities.indexOf(quality) === index)
    qualities.sort()

    const selectedBrand = searchParams.get("brand") ? searchParams.get("brand")! : "All"

    let brands = props.parts.map(part => part.brand.name)
    brands = brands.filter((brand, index) => brands.indexOf(brand) === index)
    brands.sort()

    const selectedShowOnly = searchParams.get("search_only") ? searchParams.get("search_only")! : ""

    let activeFilters = 0
    if (selectedQuality !== "All") {
        activeFilters++
    }
    if (selectedBrand !== "All") {
        activeFilters++
    }
    if (selectedShowOnly) {
        activeFilters++
    }

    const toggle = () => {
        if (props.filterSort === "filter") {
            props.setFilterSort("")
        } else {
            props.setFilterSort("filter")
        }
    }

    const changeSelectionList = (newSelectionList: string) => {
        if (newSelectionList === selectionList) {
            setSelectionList("")
            return
        }
        setSelectionList(newSelectionList)
    }

    const selectionListVars = {
        initial: {
            height: 0
        },
        animate: {
            height: "auto"
        },
        exit: {
            height: 0
        }
    }

    return (
        <div className="selection-btn" ref={props.filterRef}>
            <div className="selection__button" onClick={toggle}>
                <div className="selection-carrot">&#9662;</div>
                <div className="selection-text">
                    Filter by{activeFilters > 0 && ` (${activeFilters} active)`}
                </div>
            </div>
            <AnimatePresence>
            {props.filterSort === "filter" &&
                <motion.div className="selection__menu"
                    initial={{
                        opacity: 0
                    }}
                    animate={{
                        opacity: 1
                    }}
                >
                    <ul className="selection__menu-ul">
                        <li>
                            <a className={`selection__menu-a${selectedQuality !== "All" ? " is-active" : ""}`}
                                onClick={() => changeSelectionList("quality")}
                            >
                                Filter by Quality
                            </a>
                            <AnimatePresence>
                                {selectionList === "quality" &&
                                    <motion.ul className="selection__list"
                                        variants={selectionListVars}
                                        initial="initial"
                                        animate="animate"
                                        exit="exit"
                                    >
                                        <li>
                                            <a href={createPartsPageUrl(params.make!, params.model!,
                                                params.category, searchParams.get("year")!,
                                                searchParams.get("v")!, searchParams.get("b")!,
                                                searchParams.get("e")!, searchParams.get("t")!,
                                                "All", selectedBrand, selectedShowOnly,
                                            searchParams.get("order"))}
                                                className={`selection__list-a${selectedQuality === "All" ? " is-active" : ""}`}
                                            >
                                                All
                                            </a>
                                        </li>
                                        {qualities.map(quality =>
                                            <li key={quality}>
                                                <a href={createPartsPageUrl(params.make!, params.model!,
                                                    params.category, searchParams.get("year")!,
                                                    searchParams.get("v")!, searchParams.get("b")!,
                                                    searchParams.get("e")!, searchParams.get("t")!,
                                                    quality, selectedBrand, selectedShowOnly,
                                                    searchParams.get("order"))}
                                                    className={`selection__list-a${selectedQuality === quality ? " is-active" : ""}`}>
                                                    {quality}
                                                </a>
                                            </li>
                                        )}
                                    </motion.ul>
                                }
                            </AnimatePresence>
                        </li>
                        <li>
                            <a className={`selection__menu-a${selectedBrand !== "All" ? " is-active" : ""}`}
                                onClick={() => changeSelectionList("brand")}
                            >Filter by Brand</a>
                            <AnimatePresence>
                                {selectionList === "brand" &&
                                    <motion.ul className="selection__list"
                                        variants={selectionListVars}
                                        initial="initial"
                                        animate="animate"
                                        exit="exit"
                                    >
                                        <li>
                                            <a href={createPartsPageUrl(params.make!, params.model!,
                                                params.category, searchParams.get("year")!,
                                                searchParams.get("v")!, searchParams.get("b")!,
                                                searchParams.get("e")!, searchParams.get("t")!,
                                                selectedQuality, "All", selectedShowOnly,
                                                searchParams.get("order"))}
                                                className={`selection__list-a${selectedBrand === "All" ? " is-active" : ""}`}>All</a>
                                        </li>
                                        {brands.map(brand =>
                                            <li key={brand}>
                                                <a href={createPartsPageUrl(params.make!, params.model!,
                                                    params.category, searchParams.get("year")!,
                                                    searchParams.get("v")!, searchParams.get("b")!,
                                                    searchParams.get("e")!, searchParams.get("t")!,
                                                    selectedQuality, brand, selectedShowOnly,
                                                    searchParams.get("order"))}
                                                    className={`selection__list-a${selectedBrand === brand ? " is-active" : ""}`}>
                                                    {brand}
                                                </a>
                                            </li>
                                        )}
                                    </motion.ul>
                                }
                            </AnimatePresence>
                        </li>
                        <li>
                            <a className={`selection__menu-a${selectedShowOnly ? " is-active" : ""}`}
                                onClick={() => changeSelectionList("show only")}>
                                Show Only
                            </a>
                            <AnimatePresence>
                                {selectionList === "show only" &&
                                    <motion.ul className="selection__list"
                                        variants={selectionListVars}
                                        initial="initial"
                                        animate="animate"
                                        exit="exit"
                                    >
                                        <li>
                                            <a href={createPartsPageUrl(params.make!, params.model!,
                                                params.category, searchParams.get("year")!,
                                                searchParams.get("v")!, searchParams.get("b")!,
                                                searchParams.get("e")!, searchParams.get("t")!,
                                                selectedQuality, selectedBrand, "kits",
                                                searchParams.get("order"))}
                                                className={`selection__list-a${selectedShowOnly === "kits" ? " is-active" : ""}`}
                                            >
                                                Kits
                                            </a>
                                        </li>
                                        <li>
                                            <a href={createPartsPageUrl(params.make!, params.model!,
                                                params.category, searchParams.get("year")!,
                                                searchParams.get("v")!, searchParams.get("b")!,
                                                searchParams.get("e")!, searchParams.get("t")!,
                                                selectedQuality, selectedBrand, "parts",
                                                searchParams.get("order"))}
                                                className={`selection__list-a${selectedShowOnly === "parts" ? " is-active" : ""}`}
                                            >
                                                Parts
                                            </a>
                                        </li>
                                    </motion.ul>
                                }
                            </AnimatePresence>
                        </li>
                        <li>
                            <a className="selection__clear" href={createPartsPageUrl(params.make!,
                                params.model!, params.category, searchParams.get("year")!,
                                searchParams.get("v")!, searchParams.get("b")!,
                                searchParams.get("e")!, searchParams.get("t")!,
                                "All", "All", "", searchParams.get("order"))}>Clear Filters</a>
                        </li>
                    </ul>
                </motion.div>
            }
            </AnimatePresence>
        </div>
    )
}