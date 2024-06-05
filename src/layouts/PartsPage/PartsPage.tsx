import { useEffect, useRef, useState } from "react"
import "./PartsPage.css"
import { PartModel } from "../../model/PartModel"
import axios from "axios"
import { Link, useNavigate, useParams, useSearchParams } from "react-router-dom"
import { Part } from "./components/Part/Part"
import { VehicleListerAndCategory } from "../../components/VehicleListerAndCategory/VehicleListerAndCategory"
import { Pagination } from "../../components/Pagination/Pagination"
import { Filter } from "./components/Filter/Filter"
import { Sort } from "./components/Sort/Sort"
import { ErrorPage } from "../ErrorPage/ErrorPage"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"

export const PartsPage = () => {
    const params = useParams()
    const [searchParams] = useSearchParams()
    const navigate = useNavigate()

    const make = params.make?.split("-")[0]
    const category = params.category?.replaceAll("-", " ").replaceAll("~", "-")

    useDocumentTitle(`${make} ${params.model} ${category ? category : ""} Parts | FCP Euro`)

    const [isValid, setIsValid] = useState(true)

    const [parts, setParts] = useState<PartModel[]>([])

    const [renderPartsPage, setRenderPartsPage] = useState(false)

    const [filterSort, setFilterSort] = useState("")

    const curPage = searchParams.get("page") ? Number(searchParams.get("page")) : 1
    const partsPerPage = 10
    const idxOfLastPart = curPage * partsPerPage
    const idxOfFirstPart = idxOfLastPart - partsPerPage
    const lastPart = idxOfLastPart <= parts.length ? idxOfLastPart : parts.length
    const curParts = parts.slice(idxOfFirstPart, lastPart)

    const filterRef = useRef<HTMLDivElement>(null)
    const sortRef = useRef<HTMLDivElement>(null)

    useEffect(() => {
        const isUrlValid = async () => {
            try {
                const year = searchParams.get("year")
                const v = searchParams.get("v")
                const b = searchParams.get("b")
                const e = searchParams.get("e")
                const t = searchParams.get("t")
                await axios.post(`${process.env.REACT_APP_API}/api/v1/vehicles/is-vehicle-valid?make=${make}&model=${params.model}&year=${year}&vehicleId=${v}&bodyId=${b}&engineId=${e}&transmissionIds=${t}`)
            } catch (err) {
                console.log(err)
                setIsValid(false)
            }
        }

        isUrlValid()
    }, [])

    useEffect(() => {
        const getParts = async () => {
            try {
                let url = `${process.env.REACT_APP_API}/api/v1/parts`
                url += `?vehicleId=${searchParams.get("v")}`
                if (category) {
                    url += `&category=${category}`
                }
                const quality = searchParams.get("quality")
                if (quality) {
                    url += `&quality=${quality}`
                }
                const brand = searchParams.get("brand")
                if (brand) {
                    url += `&brand=${brand}`
                }
                const searchOnly = searchParams.get("search_only")
                if (searchOnly) {
                    url += `&search_only=${searchOnly}`
                }
                const order = searchParams.get("order")
                if (order) {
                    url += `&order=${order}`
                }
                const res = await axios.get(url)
                setParts(res.data)
            } catch (err) {
                setIsValid(false)
            }
        }

        getParts()
    }, [renderPartsPage])

    useEffect(() => {
        const handleClick = (e: any) => {
            if (filterRef.current && !filterRef.current.contains(e.target) && sortRef.current
                && !sortRef.current.contains(e.target)) {
                setFilterSort("")
            }
        }

        document.addEventListener("click", handleClick)
        return () => document.removeEventListener("click", handleClick)
    }, [])

    const paginate = (pageNumber: number) => {
        navigate(`/${params.make}/${params.model}?page=${pageNumber}&year=${searchParams.get("year")}&v=${searchParams.get("v")}&b=${searchParams.get("b")}&e=${searchParams.get("e")}&t=${searchParams.get("t")}`)
        window.scrollTo(0,0)
    }

    if (!isValid) {
        return <ErrorPage />
    }

    return (
        <div className="container">
            <div className="grid parts-page">
                <div className="grid-left">
                    <VehicleListerAndCategory setRenderPartsPage={setRenderPartsPage} />
                </div>
                <div className="grid-right">
                    <a className="parts-page-banner">
                        <img src={require("../../images/Website_Banner_LIQUIMOLY_Promo_2098x250.webp")} alt="banner" className="parts-page-banner-img" />
                    </a>
                    <ul className="crumbs">
                        <li className="crumbs-item">
                            <Link to="/" className="crumbs-link">Home</Link>
                        </li>
                        <li className="crumbs-item">
                            <Link to="" className="crumbs-link">{params.make?.split("-")[0]}</Link>
                        </li>
                        {params.category &&
                            <li className="crumbs-item">
                                <Link to="" className="crumbs-link">{category}</Link>
                            </li>
                        }
                    </ul>
                    <h1 className="parts-page-h1">
                        {params.make?.split("-")[0]} {params.model} {category} Parts
                    </h1>
                    <div className="selection">
                        <p className="selection__results">Showing {curParts.length} of {parts.length} results</p>
                        <div className="selection-cell">
                            <Filter parts={parts} filterSort={filterSort}
                                setFilterSort={setFilterSort} filterRef={filterRef} />
                            <Sort filterSort={filterSort} setFilterSort={setFilterSort} sortRef={sortRef} />
                        </div>
                    </div>
                    <div className="parts">
                        <div className="parts-heading">{make} / {category ? category : "Parts"}</div>
                        {curParts.map(p => <Part part={p} key={p.id} />)}
                    </div>
                    {parts.length > partsPerPage &&
                        <Pagination itemsPerPage={partsPerPage} length={parts.length}
                            paginate={paginate} curPage={curPage} />
                    }
                </div>
            </div>
        </div>
    )
}