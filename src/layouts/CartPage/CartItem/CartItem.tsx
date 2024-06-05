import { Link } from "react-router-dom"
import { PartModel } from "../../../model/PartModel"
import { useEffect, useState } from "react"
import axios from "axios"
import "./CartItem.css"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faAngleDown, faTrash, faHeart as fasHeart } from "@fortawesome/free-solid-svg-icons"
import { faHeart as farHeart } from "@fortawesome/free-regular-svg-icons"
import { useCart } from "../../../context/CartContext"
import { useWindowDimensions } from "../../../hooks/useWindowDimensions"
import { CartItemFetchedModel } from "../../../model/CartItemFetchedModel"

export const CartItem: React.FC<{
    cartItem: CartItemFetchedModel
}> = (props) => {
    const cart = useCart()
    const windowDimensions = useWindowDimensions()

    const [qty, setQty] = useState(String(props.cartItem.qty))

    const [isHeartRed, setIsHeartRed] = useState(false)

    const total = Math.round(props.cartItem.part.price * props.cartItem.qty * 100) / 100
    
    const changeQty = (e: React.ChangeEvent<HTMLInputElement>) => {
        setQty(e.target.value)
    }

    const remove = () => {
        cart.removeFromCart(props.cartItem.part.id)
    }

    const applyQty = () => {
        const qtyNum = Number(qty)
        if (qtyNum <= 0) {
            remove()
            return
        }
        cart.changeItemQty(props.cartItem.part.id, qtyNum)
    }

    const handleQtyBlur = (e: React.FocusEvent<HTMLInputElement, Element>) => {
        applyQty()
    }

    const qtyKeyUp = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === "Enter") {
            applyQty()
        }
    }

    const inputSelect = (e: React.MouseEvent<HTMLInputElement, MouseEvent>) => {
        (e.target as HTMLInputElement).select()
    }

    return (
        <div className="lineItem">
            <div className="lineItem__leftInfo">
                <Link to={`/products/${props.cartItem.part.url}`} className="lineItem__photo">
                    <img src={props.cartItem.part.img[0]} alt="item" />
                </Link>
                <div className="lineItem__meta">
                    <div className="lineItem__name">
                        <Link to={`/products/${props.cartItem.part.url}`}>
                            {props.cartItem.part.title.split("-")[0]}
                        </Link>
                    </div>
                    <div className="lineItem__brand">{props.cartItem.part.brand.name}</div>
                    <div className="lineItem__fulfillment">In Stock</div>
                </div>
            </div>
            <div className="lineItem__rightInfo">
                <div className="lineItem__breakdown">
                    <div className="lineItem__price">${props.cartItem.part.price}</div>
                    <div className="lineItem__quantity">
                        <FontAwesomeIcon icon={faAngleDown} className="lineItem__quantity-i" />
                        <input type="number" min={1} value={qty} onChange={changeQty}
                        onBlur={handleQtyBlur} onKeyUp={qtyKeyUp} 
                        onClick={windowDimensions.width <= 1023.98 ? inputSelect : undefined} />
                    </div>
                    <div className="lineItem__total">${total}</div>
                </div>
                <div className="lineItem__actions">
                    <div className="iconButton" onClick={remove}>
                        <FontAwesomeIcon icon={faTrash} className="iconButton-i" />
                        Remove
                    </div>
                    <div className="iconButton cart_save"
                        onMouseEnter={() => setIsHeartRed(true)}
                        onMouseLeave={() => setIsHeartRed(false)}>
                        {isHeartRed ?
                            <FontAwesomeIcon icon={fasHeart} className="iconButton-i red" />
                            :
                            <FontAwesomeIcon icon={farHeart} className="iconButton-i" />
                        }
                        Save Cart
                    </div>
                </div>
            </div>
        </div>
    )
}