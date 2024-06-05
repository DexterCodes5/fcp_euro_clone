import { useEffect, useRef, useState } from "react"
import { HeaderTopMobile } from "../HeaderTopMobile/HeaderTopMobile"
import { Link } from "react-router-dom"
import fcpEuroLogo from "../../../../svg/fcpEuroLogo.svg"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faArrowRotateLeft, faBox, faCartShopping, faCircleQuestion, faEnvelopeOpen, faMagnifyingGlass, faRightFromBracket, faTag, faToggleOff, faTruck, faUserCircle, faUsers } from "@fortawesome/free-solid-svg-icons"
import { useCart } from "../../../../context/CartContext"
import { useAuth } from "../../../../context/AuthContext"
import { Notification } from "../../../Notification/Notification"
import { ErrorNotificaition } from "../../../ErrorNotification/ErrorNotification"
import { AlertNotification } from "../../../AlertNotification/AlertNotification"

export const HeaderTop = () => {
    const auth = useAuth()
    const cart = useCart()

    const [htpWrapperHeight, setHtpWrapperHeight] = useState(0)

    const [showDropdown, setShowDropdown] = useState("")
    const dropdownState = useRef("")

    const [showMobileMenu, setShowMobileMenu] = useState(false)

    const headerTopContainerFixed = useRef<HTMLDivElement>(null)

    const setHtpWrapperHeightFunc = (height: number) => {
        setHtpWrapperHeight(height)
    }

    useEffect(() => {
        if (headerTopContainerFixed.current) {
            setHtpWrapperHeightFunc(headerTopContainerFixed.current.clientHeight)
        }
    }, [headerTopContainerFixed, showMobileMenu])

    useEffect(() => {
        const onWindowResize = () => {
            setHtpWrapperHeightFunc(headerTopContainerFixed.current?.clientHeight!)
        }

        window.addEventListener("resize", onWindowResize)
        return () => {
            window.removeEventListener("resize", onWindowResize)
        }
    }, [])

    const helpMeMouseEnter = () => {
        dropdownState.current = "help me"
        setShowDropdown("help me")
    }

    const helpMeMouseLeave = () => {
        dropdownState.current = ""
        setTimeout(() => {
            if (dropdownState.current === "")
                setShowDropdown("")
        }, 1000)
    }

    const myAccountMouseEnter = () => {
        dropdownState.current = "my account"
        setShowDropdown("my account")
    }

    const myAccountMouesLeave = () => {
        dropdownState.current = ""
        setTimeout(() => {
            if (dropdownState.current === "")
                setShowDropdown("")
        }, 1000)
    }

    const signOut = async () => {
        await auth.logout()
        window.location.href = process.env.REACT_APP_URL!
        localStorage.setItem("notification", "Signed out successfully.")
    }

    return (
        <div className="htp-wrapper" style={{ height: htpWrapperHeight }}>
            <div className="header-top-container-fixed" ref={headerTopContainerFixed}>
                <div className="header-top-container">
                    <HeaderTopMobile showMobileMenu={showMobileMenu} 
                    setShowMobileMenu={setShowMobileMenu} signOut={signOut} />
                    <div className="header-top">
                        <Link to={'/'}>
                            <img src={fcpEuroLogo} alt="logo" className="header-logo" />
                        </Link>
                        <form className="header-search">
                            <div className="hs-dropdown header-padding ">Search Entire Site</div>
                            <input className="hs-input" />
                            <button className="hs-btn">
                                <FontAwesomeIcon icon={faMagnifyingGlass} className="hs-btn-icon" />
                            </button>
                        </form>
                        <ul className="ht-right">
                            <li>
                                <a href="#">DIY Blog</a>
                            </li>
                            <li className="ht-right-long" onMouseEnter={helpMeMouseEnter} onMouseLeave={helpMeMouseLeave}>
                                <a href="#">Help me</a>
                                {showDropdown === "help me" &&
                                    <ul className="ht-right-dropdown">
                                        <li>
                                            <a href="#">
                                                <FontAwesomeIcon icon={faTruck} />
                                                <p>Track an order</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <FontAwesomeIcon icon={faArrowRotateLeft} />
                                                <p>Begin a return</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <FontAwesomeIcon icon={faBox} />
                                                <p>Shipping policy</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <FontAwesomeIcon icon={faTag} />
                                                <p>Whole sale</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <FontAwesomeIcon icon={faEnvelopeOpen} />
                                                <p>Contact our Service Team</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <FontAwesomeIcon icon={faCircleQuestion} />
                                                <p>FAQ</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <FontAwesomeIcon icon={faUsers} />
                                                <p>Careers</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <FontAwesomeIcon icon={faToggleOff} />
                                                <p>High Contrast Mode</p>
                                            </a>
                                        </li>
                                    </ul>
                                }
                            </li>
                            <li className="ht-right-long" onMouseEnter={myAccountMouseEnter} onMouseLeave={myAccountMouesLeave}>
                                <a href="#">My Account</a>
                                {showDropdown === "my account" &&
                                    <ul className="ht-right-dropdown-ma">
                                        <li>
                                            <Link to="/account">
                                                <FontAwesomeIcon icon={faUserCircle} />
                                                <p>View Account</p>
                                            </Link>
                                        </li>
                                        <li>
                                            {auth.isAuthenticated() ?
                                                <a onClick={signOut}>
                                                    <FontAwesomeIcon icon={faRightFromBracket} />
                                                    <p>Sign out</p>
                                                </a>
                                                :
                                                <Link to="/login">
                                                    <FontAwesomeIcon icon={faRightFromBracket} />
                                                    <p>Sign in</p>
                                                </Link>
                                            }
                                        </li>
                                    </ul>
                                }
                            </li>
                            <li>
                                <Link to={`/cart`}>
                                    <FontAwesomeIcon icon={faCartShopping} />
                                    <div className="cart-quantity">{cart.cartItems.length}</div>
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>
                <Notification />
                <ErrorNotificaition />
                <AlertNotification />
            </div>
        </div>
    )
}