import { Link, useNavigate } from "react-router-dom"
import "../../../../css/Checkout.css"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faAngleLeft } from "@fortawesome/free-solid-svg-icons"
import { OrderSummary } from "../../../../components/OrderSummary/OrderSummary"
import { useEffect, useState } from "react"
import { AddressModel } from "../../../../model/checkout/AddressModel"
import { useCart } from "../../../../context/CartContext"
import { ErrorPage } from "../../../ErrorPage/ErrorPage"
import { useDocumentTitle } from "../../../../hooks/useDocumentTitle"
import { useCheckoutNavigate } from "../../../../hooks/useCheckoutNavigate"

const countriesAndStates = [
    {
        country: "Bulgaria",
        states: [
            "Burgas",
            "Varna",
            "Veliko Turnovo",
            "Gabrovo",
            "Sofia"
        ]
    },
    {
        country: "US",
        states: [
            "California",
            "Colorado",
            "Hawai"
        ]
    },
    {
        country: "UK",
        states: [
            "Bedford",
            "Birmingham",
            "Blackpool"
        ]
    }
]

export const AddressPage = () => {
    useDocumentTitle("You Address | FCP Euro")
    const navigate = useNavigate()
    const cart = useCart()

    const [address, setAddress] = useState<AddressModel>(() => {
        const addressJson = localStorage.getItem("address")
        if (!addressJson || addressJson === "null") {
            return new AddressModel("", "", "", "", "", "", "", "", "", "")
        }
        return JSON.parse(addressJson)
    })

    useEffect(() => {
        localStorage.setItem("checkout", "address")
    }, [])

    const changeAddress = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target
        setAddress({ ...address, [name]: value })
    }

    const clearAll = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setAddress(new AddressModel("", "", "", "", "", "", "", "", "", ""))
    }

    const addressSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        localStorage.setItem("address", JSON.stringify(address))
        localStorage.setItem("checkout", "delivery")
        navigate("/checkout/delivery")
    }

    if (cart.cartItems.length === 0) {
        return <ErrorPage />
    }

    return (
        <div className="checkout">
            <div className="checkout__container grid-container">
                <form className="checkout_form_address" onSubmit={addressSubmit}>
                    <div className="checkout__nav">
                        {/* ProgressTracker should be written on every page, because it's too dynamic */}
                        <div className="progressTracker">
                            <Link to="/cart" className="progressTracker__step progressTracker--isComplete">
                                <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                                CART
                            </Link>
                            <a href="" className="progressTracker__step progressTracker--isCurrent">
                                <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                                ADDRESS
                            </a>
                            <a href="" className="progressTracker__step">
                                <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                                DELIVERY
                            </a>
                            <a href="" className="progressTracker__step">
                                <FontAwesomeIcon icon={faAngleLeft} className="progressTracker__step-i" />
                                PAYMENT
                            </a>
                        </div>
                    </div>
                    <div className="checkout__panes">
                        <div className="checkout__leftPane">
                            <div className="addressBlocks">
                                <h1 className="checkout__title">Billing Address</h1>
                                <div className="fieldGroup">
                                    <div className="fieldGroup__row fieldGroup--rowIsCollapsible">
                                        <div className="fieldGroup__validated">
                                            <input type="text" placeholder="First name" required name="firstName" value={address.firstName} onChange={changeAddress} />
                                        </div>
                                        <div className="fieldGroup__validated">
                                            <input type="text" placeholder="Last name" required name="lastName" value={address.lastName} onChange={changeAddress} />
                                        </div>
                                    </div>
                                    <div className="fieldGroup__row">
                                        <div className="fieldGroup__validated">
                                            <input type="text" placeholder="Street address" required name="streetAddress" value={address.streetAddress}
                                                onChange={changeAddress} />
                                        </div>
                                    </div>
                                    <div className="fieldGroup__row">
                                        <div className="fieldGroup__validated">
                                            <input type="text" placeholder="Street address (cont'd)"
                                                name="streetAddressContd"
                                                value={address.streetAddressContd}
                                                onChange={changeAddress} />
                                        </div>
                                    </div>
                                    <div className="fieldGroup__row">
                                        <div className="fieldGroup__validated">
                                            <input type="text" placeholder="Company Name" name="companyName" value={address.companyName} onChange={changeAddress} />
                                        </div>
                                    </div>
                                    <div className="fieldGroup__row">
                                        <div className="fieldGroup__validated">
                                            <input type="text" placeholder="City" name="city" required
                                                value={address.city} onChange={changeAddress} />
                                        </div>
                                    </div>
                                    <div className="fieldGroup__row">
                                        <div className="fieldGroup__validated">
                                            <select id="country" required name="country" value={address.country} onChange={changeAddress}>
                                                <option value="">Select country</option>
                                                {countriesAndStates.map(c =>
                                                    <option value={c.country} key={c.country}>{c.country}</option>
                                                )}
                                            </select>
                                        </div>
                                        <div className="fieldGroup__validated">
                                            <select name="state" id="state" required value={address.state}
                                                onChange={changeAddress}>
                                                <option value="">Select state</option>
                                                {countriesAndStates.find(c => c.country === address.country)?.states.map(state =>
                                                    <option value={state} key={state}>{state}</option>
                                                )}
                                            </select>
                                        </div>
                                    </div>
                                    <div className="fieldGroup__row fieldGroup--rowIsCollapsible">
                                        <div className="fieldGroup__validated">
                                            <input type="text" placeholder="Zip code" required name="zipCode"
                                                value={address.zipCode} onChange={changeAddress} />
                                        </div>
                                        <div className="fieldGroup__validated">
                                            <input type="text" placeholder="Phone number" required
                                                name="phoneNumber" value={address.phoneNumber}
                                                onChange={changeAddress} />
                                        </div>
                                    </div>
                                </div>
                                <div className="addressBlocks__options">
                                    <button className="secondaryCta secondaryCta--medium" 
                                    onClick={clearAll}>Clear all</button>
                                </div>
                            </div>
                        </div>
                        <div className="checkout__rightPane">
                            <OrderSummary tax={0}
                                Button={
                                    <input type="submit" value="Continue To Shipping"
                                        className="orderSummary_cta" />
                                }
                            />
                        </div>
                    </div>
                </form>
            </div>
        </div>
    )
}