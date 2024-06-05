import { useNavigate } from "react-router-dom"
import { PartModel } from "../../../../model/PartModel"
import "./Part.css"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faCheck, faStar, faTruck } from "@fortawesome/free-solid-svg-icons"
import { useEffect, useState } from "react"
import { useCart } from "../../../../context/CartContext"
import axios from "axios"
import { FitmentModel } from "../../../../model/fitment/FitmentModel"

export const Part: React.FC<{
    part: PartModel
}> = (props) => {
    const navigate = useNavigate()
    const cart = useCart()

    const [qty, setQty] = useState(1)

    const [fitments, setFitments] = useState<FitmentModel[]>([])

    useEffect(() => {
        const getFitments = async () => {
            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/fitment?partId=${props.part.id}`)
            setFitments(res.data)
        }

        getFitments()
    }, [])

    const addToCart = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
        e.stopPropagation()
        cart.increaseItemQty(props.part.id, qty)
    }
    
    return (
        <div className="grid-x part"
            onClick={() => navigate(`/products/${props.part.url}`)}>
            <div className="cell large-3">
                <div className="part-img-container">
                    <img src={props.part.img[0]} alt="part" className="part-img" />
                </div>
            </div>
            <div className="cell large-6">
                <div className="part-body">
                    <p className="part-title">{props.part.title}</p>
                    <p className="part-other-models">{fitments.map(fitment => fitment.model).join(", ")}</p>
                    {props.part.brand.img ?
                        <img src={props.part.brand.img} alt="brand" />
                        :
                        <p className="part__brandName">{props.part.brand.name}</p>
                    }
                    <div className="rating-stars">
                        <FontAwesomeIcon icon={faStar} />
                        <FontAwesomeIcon icon={faStar} />
                        <FontAwesomeIcon icon={faStar} />
                        <FontAwesomeIcon icon={faStar} />
                        <FontAwesomeIcon icon={faStar} />
                    </div>
                    <p className="part-ratings">1 Rating</p>
                </div>
            </div>
            <div className="cell large-3">
                <div className="grid-x part-buy">
                    <div className="cell large-12 small-4 part-availability">
                        <FontAwesomeIcon icon={faCheck} className="part__check" />
                        <span>Available</span>
                        <p className="part-stock">In Stock</p>
                    </div>
                    <div className="cell large-12 small-4 part-ship">
                        <FontAwesomeIcon icon={faTruck} className="part-truck" />
                        &nbsp;Ships Free
                    </div>
                    <div className="cell large-12 small-4 part-qty-buy">
                        <div className="part-qty">
                            <span className="part-qty-span">QTY </span>
                            <input className="part-qty-input" type="number" min={1} value={qty}
                                onChange={(e) => setQty(Number(e.target.value))} onClick={e => e.stopPropagation()} />
                        </div>
                        <div className="part-add" onClick={addToCart}>
                            <span className="part-add-price">${props.part.price}</span>
                            <span className="part-add-plus">+</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}