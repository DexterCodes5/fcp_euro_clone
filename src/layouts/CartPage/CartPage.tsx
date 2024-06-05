import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import "./CartPage.css"
import { faAngleLeft, faShare, faHeart as fasHeart } from "@fortawesome/free-solid-svg-icons"
import { HeaderTop } from "../../components/Header/components/HeaderTop/HeaderTop"
import { Footer } from "../../components/Footer/Footer"
import { faHeart as farHeart } from "@fortawesome/free-regular-svg-icons"
import { useEffect, useState } from "react"
import { useCart } from "../../context/CartContext"
import { CartItem } from "./CartItem/CartItem"
import { Link } from "react-router-dom"
import { CartItemFetchedModel } from "../../model/CartItemFetchedModel"
import axios from "axios"
import { useLocalStorage } from "../../hooks/useLocalStorage"
import { SavedVehicleModel } from "../../model/SavedVehicleModel"
import "../../css/Checkout.css"
import { OrderSummary } from "../../components/OrderSummary/OrderSummary"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"

export const CartPage = () => {
    useDocumentTitle("Your Cart | FCP Euro")

    const cart = useCart()

    const [savedVehicles] = useLocalStorage<SavedVehicleModel[]>("saved-vehicles", [])

    const [isHeartRed, setIsHeartRed] = useState(false)

    const [cartItems, setCartItems] = useState<CartItemFetchedModel[]>([])

    const [checkoutState, setCheckoutState] = useLocalStorage("checkout", "")

    // I need to fetch the parts in CartPage and not CartItem, so I can calculate Total
    useEffect(() => {
        const getParts = async () => {
            // Fetch the parts one by one, so they can persist order
            const newCartItems: CartItemFetchedModel[] = []

            for (const item of cart.cartItems) {
                const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/parts/get-part-by-id/${item.id}`)
                newCartItems.push(new CartItemFetchedModel(res.data, item.qty))
            }
            setCartItems(newCartItems)
        }

        getParts()
    }, [cart.cartItems])

    return (
        <>
            <HeaderTop />
            <div className="checkout">
                <div className="checkout__container grid-container">
                    <div className="checkout__nav">
                        <Link to={savedVehicles.length > 0 ?
                            `/${savedVehicles[0].make?.make}-parts/${savedVehicles[0].baseVehicle?.model}?year=${savedVehicles[0].baseVehicle?.year}&v=${savedVehicles[0].vehicle?.id}&b=${savedVehicles[0].body?.id}&e=${savedVehicles[0].engine?.id}&t=${savedVehicles[0].transmission?.id}`
                            :
                            "/"} className="returnLink">
                            <FontAwesomeIcon icon={faAngleLeft} className="returnLink-i" />
                            CONTINUE SHOPPING
                        </Link>
                    </div>
                    <div className="checkout__panes">
                        <div className="checkout__leftPane">
                            <ul className="tabs cartTabs">
                                <li className="is-active">
                                    <a href="#">Shopping Cart ({cart.cartItems.length})</a>
                                </li>
                            </ul>
                            {cart.cartItems.length > 0 ?
                                <div className="cart">
                                    <div className="cart__header">
                                        <h1 className="cart__headerTitle">Your Cart</h1>
                                        <div className="cart__options">
                                            <div className="iconButton">
                                                <FontAwesomeIcon icon={faShare} className="iconButton-i" />
                                                Share Cart
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
                                    <ul className="cart__lineHeader">
                                        <li>{cart.cartItems.length} Item{cart.cartItems.length > 1 && "s"}</li>
                                        <li>Price</li>
                                        <li>Quantity</li>
                                        <li>Total</li>
                                    </ul>
                                    <div className="cart__items">
                                        {cartItems?.map(cartItem =>
                                            <CartItem cartItem={cartItem} key={cartItem.part.id} />
                                        )}
                                    </div>
                                    <div className="cart__gurantee">
                                        <img src={require("../../images/gurantee.webp")} alt="gurantee" className="cart__lifetimeCreative" />
                                        <h3 className="cart__guranteeStatement">FREE Lifetime Replacement Guarantee</h3>
                                    </div>
                                </div>
                                :
                                <div className="cart">
                                    <div className="cart__header">
                                        <h1 className="cart__headerTitle" 
                                        style={{ marginBottom: "1rem"}}>
                                            Your Cart is empty
                                        </h1>
                                    </div>
                                </div>
                            }
                        </div>
                        <div className="checkout__rightPane">
                            {cart.cartItems.length > 0 &&
                                <OrderSummary tax={0}
                                    Button={
                                        <Link to={`/checkout/${checkoutState}`} className="orderSummary_cta">Begin Checkout</Link>
                                    } />
                            }
                        </div>
                    </div>
                </div>
            </div >
            <Footer />
        </>
    )
}