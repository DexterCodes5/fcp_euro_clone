import { useState } from "react"
import { useParams, useSearchParams } from "react-router-dom"
import { createPartsPageUrl } from "../../../../util/UrlUtils"
import { AnimatePresence, motion } from "framer-motion"

export const Sort: React.FC<{
    filterSort: string, setFilterSort: React.Dispatch<React.SetStateAction<string>>, 
    sortRef: React.RefObject<HTMLDivElement>
}> = (props) => {
    const params = useParams()
    const [searchParams] = useSearchParams()

    const sort = searchParams.get("order")
    let selectionText
    if (sort === null) {
        selectionText = "Sort by"
    } else if (sort === "ascend_by_price") {
        selectionText = "Price: Low to High"
    } else if (sort === "descend_by_price") {
        selectionText = "Price: High to Low"
    }

    const toggle = () => {
        console.log("here")
        if (props.filterSort === "sort") {
            props.setFilterSort("")
        } else {
            props.setFilterSort("sort")
        }
    }

    const createUrl = (sortOption: string | null) => {
        return createPartsPageUrl(params.make!, params.model!, params.category,
            searchParams.get("year")!,
            searchParams.get("v")!, searchParams.get("b")!, searchParams.get("e")!,
            searchParams.get("t")!,
            searchParams.get("quality"), searchParams.get("brand"), searchParams.get("search_only"),
            sortOption
        )
    }

    return (
        <div className="selection-btn" ref={props.sortRef}>
            <div className="selection__button" onClick={toggle}>
                <div className="selection-carrot">&#9662;</div>
                <div className="selection-text">{selectionText}</div>
            </div>
            <AnimatePresence>
            {props.filterSort === "sort" &&
                <motion.div className="selection__menu selection__menu--right"
                    initial={{
                        opacity: 0
                    }}
                    animate={{
                        opacity: 1
                    }}
                >
                    <ul className="selection__menu-ul">
                        <li>
                            <a href={createUrl("ascend_by_price")}>Price: Low to High</a>
                        </li>
                        <li>
                            <a href={createUrl("descend_by_price")}>Price: High to Low</a>
                        </li>
                        <li>
                            <a href={createUrl(null)} className="selection__clear">Clear Sort</a>
                        </li>
                    </ul>
                </motion.div>
            }
            </AnimatePresence>
        </div>
    )
}