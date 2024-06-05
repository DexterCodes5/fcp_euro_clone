import { Link } from "react-router-dom"
import "./Pagination.css"

export const Pagination: React.FC<{
    itemsPerPage: number, length: number, paginate: Function, curPage: number
}> = (props) => {
    const paginationNumbers = []

    for (let i = 1; i <= Math.ceil(props.length / props.itemsPerPage); i++) {
        paginationNumbers.push(i)
    }

    return (
        <div className="pages">
            {props.curPage > 1 &&
                <span className="pages__span">
                    <a className="pages__link" onClick={() => props.paginate(props.curPage-1)}>
                        &#9666;
                    </a>
                </span>
            }
            {paginationNumbers.map((pageNumber) =>
                <span className="pages__span" key={pageNumber}>
                    <a className={pageNumber === props.curPage ? "pages__link--current" : "pages__link"} onClick={() => props.paginate(pageNumber)}>
                        {pageNumber}
                    </a>
                </span>
            )}
            {props.curPage < paginationNumbers.length &&
                <span className="pages__span">
                    <a className="pages__link" onClick={() => props.paginate(props.curPage+1)}>
                        &#9656;
                    </a>
                </span>
            }
        </div>
    )
}