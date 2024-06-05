import { Link, Navigate, useNavigate } from "react-router-dom"
import "../../../../css/Checkout.css"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faAngleLeft, faCreditCard, faLock } from "@fortawesome/free-solid-svg-icons"
import { OrderSummary } from "../../../../components/OrderSummary/OrderSummary"
import "./PaymentPage.css"
import { useEffect, useState } from "react"
import PaypalSvg from "../../../../svg/paypal.svg"
import { AnimatePresence, motion } from "framer-motion"
import { useLocalStorage } from "../../../../hooks/useLocalStorage"
import { AddressModel } from "../../../../model/checkout/AddressModel"
import { DeliveryModel } from "../../../../model/checkout/DeliveryModel"
import { CustomerOrderModel } from "../../../../model/checkout/CustomerOrderModel"
import { useCart } from "../../../../context/CartContext"
import axios from "axios"
import { useAuth } from "../../../../context/AuthContext"
import { useDocumentTitle } from "../../../../hooks/useDocumentTitle"
import { SpinnerLoading } from "../../../../components/SpinnerLoading/SpinnerLoading"

export const PaymentPage = () => {
    useDocumentTitle("Payment Details | FCP Euro")
    const cart = useCart()
    const auth = useAuth()
    const navigate = useNavigate()

    const [paymentMethod, setPaymentMethod] = useState("new credit card")

    const [checkoutState] = useLocalStorage("checkout", "")
    const [guestEmail] = useLocalStorage<string | null>("guest-email", "")
    const [address] = useLocalStorage<AddressModel | null>("address", null)
    const [delivery] = useLocalStorage<DeliveryModel | null>("delivery", null)

    const [isLoading, setIsLoading] = useState(false)
    const [error, setError] = useState(false)

    const navigateBack = (to: string) => {
        localStorage.setItem("checkout", to)
        navigate(`/checkout/${to}`)
    }

    const submitOrder = async () => {
        setIsLoading(true)
        const total = Math.round((cart.subtotal + delivery!.price) * 100) / 100
        let email = guestEmail
        if (auth.isAuthenticated()) {
            email = (await auth.getUser()).email
        }
        const customerOrder = new CustomerOrderModel(email!, cart.cartItems, address!, delivery!,
            paymentMethod, cart.subtotal, delivery!.price, 0, total
        )
        try {
            if (auth.isAuthenticated()) {
                const accessToken = await auth.getAccessToken()
                await axios.post(`${process.env.REACT_APP_API}/api/v1/customer-orders/receive-order-request`, customerOrder, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
            } else {
                await axios.post(`${process.env.REACT_APP_API}/api/v1/customer-orders/receive-order-request-guest`, customerOrder)
            }
        } catch (err) {
            setError(true)
            setIsLoading(false)
            return
        }
        cart.emptyCart()
        localStorage.setItem("delivery", "null")
        localStorage.setItem("checkout", "address")
        navigate("/")
    }

    if (checkoutState !== "payment") {
        return <Navigate to={`/checkout/${checkoutState}`} />
    }

    return (
        <div className="checkout">
            {isLoading && <SpinnerLoading />}
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
                        <a className="progressTracker__step progressTracker--isComplete"
                            onClick={() => navigateBack("delivery")}>
                            <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                            DELIVERY
                        </a>
                        <a className="progressTracker__step progressTracker--isCurrent">
                            <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                            PAYMENT
                        </a>
                    </div>
                </div>
                <div className="checkout__panes">
                    <div className="checkout__leftPane">
                        <div className="payment">
                            <div className="payment__header">
                                <h1 className="checkout__title payment__title">
                                    Payment Method
                                    <FontAwesomeIcon icon={faLock} className="payment__title-i" />
                                </h1>
                            </div>
                            <ul className="paymentTabs">
                                <li className={`${paymentMethod === "new credit card" ? "paymentTabs--isSelected" : ""}`} onClick={() => setPaymentMethod("new credit card")}>
                                    <FontAwesomeIcon icon={faCreditCard} className="paymentTabs__cardIcon" />
                                    <span>New Credit Card</span>
                                </li>
                                <li className={`${paymentMethod === "paypal" ? "paymentTabs--isSelected" : ""}`} onClick={() => setPaymentMethod("paypal")}>
                                    <FontAwesomeIcon icon={faCreditCard} className="paymentTabs__cardIcon" />
                                    <span>Paypal</span>
                                </li>
                            </ul>
                            <AnimatePresence initial={false}>
                                {paymentMethod === "new credit card" &&
                                    <motion.section
                                        initial={{
                                            height: 0,
                                            marginTop: 0
                                        }}
                                        animate={{
                                            height: "auto",
                                            marginTop: 20
                                        }}
                                        exit={{
                                            height: 0,
                                            marginTop: 0
                                        }}
                                        transition={{
                                            duration: .5
                                        }}
                                    >
                                        <div className="payment__newCardTitle payment__title">
                                            New Card Information
                                            <FontAwesomeIcon icon={faLock} className="payment__title-i" />
                                        </div>
                                        <div className="payment__cardForm">
                                            <div className="payment_field">
                                                <input type="text" placeholder="**** **** **** ****"
                                                    className="payment-input" />
                                            </div>
                                            <div className="payment__row">
                                                <div className="payment_field">
                                                    <input type="text" placeholder="MM/YY"
                                                        className="payment-input" />
                                                </div>
                                                <div className="payment_field">
                                                    <input type="text" placeholder="CVC"
                                                        className="payment-input" />
                                                </div>
                                            </div>
                                        </div>
                                    </motion.section>
                                }
                            </AnimatePresence>
                            <AnimatePresence>
                                {paymentMethod === "paypal" &&
                                    <motion.section
                                        initial={{
                                            height: 0,
                                            marginTop: 0
                                        }}
                                        animate={{
                                            height: "auto",
                                            marginTop: 20
                                        }}
                                        exit={{
                                            height: 0,
                                            marginTop: 0
                                        }}
                                        transition={{
                                            duration: .5
                                        }}
                                    >
                                        <div className="payment__paypal">
                                            <div className="paypal-btn">
                                                <img src={PaypalSvg} alt="" />
                                            </div>
                                            <div className="paypal-tagline">The safer, easier way to pay</div>
                                        </div>
                                    </motion.section>
                                }
                            </AnimatePresence>
                        </div>
                    </div>
                    <div className="checkout__rightPane">
                        <OrderSummary tax={0} Button={
                            <button className="orderSummary_cta" onClick={submitOrder}>
                                Submit Order
                            </button>}
                        />
                        {error &&
                            <div className="checkout__error">Something went wrong.<br></br>Please try againg later.</div>
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}