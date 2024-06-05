import { Link, Navigate, useNavigate } from "react-router-dom"
import "../../../../css/Checkout.css"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faAngleLeft, faMobileAlt } from "@fortawesome/free-solid-svg-icons"
import { OrderSummary } from "../../../../components/OrderSummary/OrderSummary"
import { DeliveryModel } from "../../../../model/checkout/DeliveryModel"
import { useEffect, useState } from "react"
import { useLocalStorage } from "../../../../hooks/useLocalStorage"
import axios from "axios"
import { AddressModel } from "../../../../model/checkout/AddressModel"
import { ErrorPage } from "../../../ErrorPage/ErrorPage"
import { useDocumentTitle } from "../../../../hooks/useDocumentTitle"
import { useCheckoutNavigate } from "../../../../hooks/useCheckoutNavigate"

type DeliveryMethod = {
    price: number
    receiveDate: string
}

export const DeliveryPage = () => {
    useDocumentTitle("Shipping Options | FCP Euro")
    const navigate = useNavigate()

    const [checkoutState, setCheckoutState] = useLocalStorage<string>("checkout", "")
    // I need address
    const [address, setAddress] = useLocalStorage<AddressModel | null>("address", null)
    const [deliveryMethods, setDeliveryMethods] = useState<DeliveryMethod[]>([])
    const [selectedDelivery, setSelectedDelivery] = useState<DeliveryModel>()

    useEffect(() => {
        const getDeliveries = async () => {
            if (!address) {
                return
            }

            const res = await axios.post(`${process.env.REACT_APP_API}/api/v1/deliveries`, address)
            setDeliveryMethods(res.data)
            if (res.data.length > 0) {
                setSelectedDelivery(new DeliveryModel(false, res.data[0].price, res.data[0].receiveDate))
            } else {
                console.error("No delivery methods available")
            }
        }

        getDeliveries()
    }, [address])

    const navigateBack = (to: string) => {
        localStorage.setItem("checkout", to)
        navigate(`/checkout/${to}`)
    }

    const changeDeliveryReceiveTextNotification = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSelectedDelivery({ ...selectedDelivery!, receiveTextNotification: e.target.checked })
    }

    const renderDeliveryMethod = (deliveryMethod: DeliveryMethod) => {
        const dateChunks = new Date(deliveryMethod.receiveDate).toString().split(" ")

        return (
            <label htmlFor="order_shipments" className="delivery__shippingMethod delivery--methodSelected" key={deliveryMethod.price + deliveryMethod.receiveDate}>
                <div className="delivery__flexCenterWrapper">
                    <label htmlFor="order_shipments" className="delivery__checkBox">
                        <input type="checkbox" id="order_shipments" defaultChecked />
                        <span></span>
                    </label>
                    <div className="delivery__methodPrice">${deliveryMethod.price}</div>
                </div>
                <div className="delivery__methodDelivery">
                    Estimated<br />Delivery
                </div>
                <div className="delivery__methodWindow">
                    {dateChunks[0]}, {dateChunks[1]} {dateChunks[2]}
                </div>
            </label>
        )
    }

    const continueToPayment = () => {
        localStorage.setItem("delivery", JSON.stringify(selectedDelivery))
        localStorage.setItem("checkout", "payment")
        navigate("/checkout/payment")
    }

    if (checkoutState !== "delivery") {
        return <Navigate to={`/checkout/${checkoutState}`} />
    }

    return (
        <div className="checkout">
            <div className="checkout__container grid-container">
                <div className="checkout__nav">
                    {/* ProgressTracker should be written on every page, because it's too dynamic */}
                    <div className="progressTracker">
                        <Link to="/cart" className="progressTracker__step progressTracker--isComplete">
                            <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                            CART
                        </Link>
                        <a className="progressTracker__step progressTracker--isComplete"
                        onClick={() => navigateBack("address")}>
                            <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                            ADDRESS
                        </a>
                        <a className="progressTracker__step progressTracker--isCurrent">
                            <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                            DELIVERY
                        </a>
                        <a className="progressTracker__step">
                            <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                            PAYMENT
                        </a>
                    </div>
                </div>
                <div className="checkout__panes">
                    <div className="checkout__leftPane">
                        <div className="delivery">
                            <h1 className="checkout__title">Select a Shipping Service</h1>
                            <label className="textUpdates" htmlFor="order_send_sms">
                                <label className="textUpdates__checkbox" htmlFor="order_send_sms">
                                    <input type="checkbox" id="order_send_sms"
                                        checked={selectedDelivery ? selectedDelivery.receiveTextNotification : true}
                                        onChange={changeDeliveryReceiveTextNotification}
                                    />
                                    <span></span>
                                </label>
                                <div className="textUpdates__desc">
                                    <p>
                                        Receive text notifications for this order?&nbsp;
                                        <FontAwesomeIcon icon={faMobileAlt} />
                                    </p>
                                    <p>
                                        Message and data rates may apply, FCP Euro's <a href="">Terms and Conditions</a> and <a href="">Privacy Policy</a>
                                    </p>
                                </div>
                            </label>
                            <div className="delivery__method grid-x grid-margin-x grid-margin-y">
                                <div className="cell small-6 medium-4 large-3">
                                    {deliveryMethods.map(renderDeliveryMethod)}
                                </div>
                            </div>
                            <div className="delivery__info">
                                <h4>International Shipping Information</h4>
                                <div className="delivery__disclaimer">Additional taxes, duties, or brokerage fees may apply and must be paid upon delivery. FCP Euro does not have a way to determine this charge prior to shipping. FCP Euro is not responsible for these charges.</div>
                            </div>
                        </div>
                    </div>
                    <div className="checkout__rightPane">
                        <OrderSummary tax={0}
                            Button={
                                <button className="orderSummary_cta" onClick={continueToPayment}>Continue To Payment</button>
                            }
                        />
                    </div>
                </div>
            </div>
        </div>
    )
}