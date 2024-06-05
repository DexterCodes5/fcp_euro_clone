import { Link, Outlet, useNavigate } from "react-router-dom"
import { Footer } from "../../components/Footer/Footer"
import fcpEuroLogo from "../../svg/fcpEuroLogo.svg"
import "./CheckoutLayout.css"
import { useEffect } from "react"
import { useCart } from "../../context/CartContext"
import { useAuth } from "../../context/AuthContext"

export const CheckoutLayout = () => {
    const cart = useCart()
    const navigate = useNavigate()
    const auth = useAuth()

    useEffect(() => {
        if (cart.cartItems.length === 0) {
            navigate("/cart")
        }
    }, [])

    useEffect(() => {
        const guestEmail = localStorage.getItem("guest-email")
        if (!auth.isLoading) {
            if (!auth.isAuthenticated() && !guestEmail) {
                localStorage.setItem("checkout", "address")
                navigate("/checkout/address")
            } else {
                const checkoutState = localStorage.getItem("checkout")
                navigate(`/checkout/${checkoutState}`)
            }
        }
    }, [auth.isLoading])

    return (
        <>
            <header className="topBar">
                <div className="topBar__container">
                    <div className="topBar__header">
                        <Link to="/">
                            <img src={fcpEuroLogo} alt="logo" className="topBar__logo" />
                        </Link>
                    </div>
                    <div className="topBar__headerMobile">
                        <Link to="/">
                            <img src={fcpEuroLogo} alt="logo" className="topBar__logoMobile" />
                        </Link>
                    </div>
                </div>
            </header>
            <Outlet />
            <Footer />
        </>
    )
}