import { faQuestionCircle } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import "./OrderSummary.css"
import { useCart } from "../../context/CartContext"
import { useLocalStorage } from "../../hooks/useLocalStorage"
import { DeliveryModel } from "../../model/checkout/DeliveryModel"

export const OrderSummary: React.FC<{
    tax: number, Button: JSX.Element | null
}> = (props) => {
    const cart = useCart()

    const [delivery, setDelivery] = useLocalStorage<DeliveryModel | null>("delivery", null)

    const [progress, setProgress] = useState("0")

    const shipping = delivery ? delivery.price : 0

    const total = (Math.round((cart.subtotal + shipping + props.tax) * 100) / 100).toFixed(2)

    useEffect(() => {
        setProgress("100%")
    }, [])

    return (
        <div className="orderSummary">
            <div className="orderSummary__panel">
                <div className="orderSummary__header">Order Summary</div>
                <div className="orderSummary__container">
                    <div className="orderSummary__refNumber">
                        Order Number:
                        <Link to={'/cart'}>R358408809</Link>
                    </div>
                    <ul className="orderSummary__breakdown">
                        <li>
                            <span>Subtotal</span>
                            <span>${cart.subtotal}</span>
                        </li>
                        <li>
                            <span>Shipping</span>
                            <span>${shipping}</span>
                        </li>
                        <li>
                            <span>Tax</span>
                            <span>${props.tax}</span>
                        </li>
                        <li>
                            <span>Total</span>
                            <span>${total}</span>
                        </li>
                    </ul>
                    <div className="orderSummary__freeShipping">
                        <div className="orderSummary__caption">
                            <strong>Free Shipping </strong>
                            <span>within the contig. U.S. </span>
                            <FontAwesomeIcon icon={faQuestionCircle}
                                className="orderSummary__caption-i" />
                        </div>
                        <div className="progressBar__track">
                            <div className="progressBar__bar" style={{ width: progress }}>
                            </div>
                        </div>
                    </div>
                    <div className="orderSummary_embeddedForm">
                        <input type="text" placeholder="Enter coupon code" className="orderSummary__embeddedInput" />
                        <button className="orderSummary__embeddedSubmit">Apply</button>
                    </div>
                </div>
            </div>
            {props.Button && props.Button}
        </div>
    )
}