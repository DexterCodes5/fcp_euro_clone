import { useState } from "react"
import fcpEuroLogo from "../../../../svg/fcpEuroLogo.svg"
import "./HeaderTopMobile.css"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faArrowRotateLeft, faBox, faCartShopping, faCircleQuestion, faEnvelopeOpen, faMagnifyingGlass, faMagnifyingGlassMinus, faRightFromBracket, faTag, faToggleOff, faTruck, faUserCircle, faUsers } from "@fortawesome/free-solid-svg-icons"
import { AnimatePresence, motion } from "framer-motion"
import { Link } from "react-router-dom"
import { useAuth } from "../../../../context/AuthContext"

export const HeaderTopMobile: React.FC<{
    showMobileMenu: boolean, setShowMobileMenu: React.Dispatch<React.SetStateAction<boolean>>,
    signOut: () => Promise<void>
}> = (props) => {
    const auth = useAuth()

    const [showSearch, setShowSearch] = useState(false)

    const [showHelpMe, setShowHelpMe] = useState(false)

    const [showMyAccount, setShowMyAccount] = useState(false)

    const menuVars = {
        initial: {
            height: 0
        },
        animate: {
            height: 347
        },
        exit: {
            height: 0
        }
    }

    return (
        <div className="header-top-mobile">
            <div className="header-top-mobile-top">
                <div className={`hamburger-container${props.showMobileMenu ? " hamburger-active" : ""}`}
                    onClick={() => props.setShowMobileMenu(!props.showMobileMenu)}>
                    <p className="hamburger"></p>
                </div>
                <img src={fcpEuroLogo} alt="logo" className="header-logo-mobile" />
                <div className="htm-sc">
                    <FontAwesomeIcon className="htm-sc-icon" icon={showSearch ? faMagnifyingGlassMinus : faMagnifyingGlass} onClick={() => setShowSearch(!showSearch)} />
                    <FontAwesomeIcon className="htm-sc-icon" icon={faCartShopping} />
                </div>
            </div>
            {props.showMobileMenu &&
                <ul className="htm-menu">
                    <li>
                        <a>
                            DIY Blog
                        </a>
                    </li>
                    <li>
                        <a className={`htm-menu-a-triangle ${showHelpMe ? " htm-menu-a-triangle-active" : ""}`} onClick={() => setShowHelpMe(!showHelpMe)}>
                            Help Me
                        </a>
                        <AnimatePresence>
                            {showHelpMe &&
                                <motion.ul variants={menuVars} initial="initial" animate="animate" exit="exit" className="htm-dropdown">
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
                                </motion.ul>
                            }
                        </AnimatePresence>
                    </li>
                    <li>
                        <a className={`htm-menu-a-triangle${showMyAccount ? " htm-menu-a-triangle-active" : ""}`} onClick={() => setShowMyAccount(!showMyAccount)}>
                            My Account
                        </a>
                        {showMyAccount &&
                            <ul className="htm-dropdown">
                                <li>
                                    <Link to="/account">
                                        <FontAwesomeIcon icon={faUserCircle} />
                                        <p>View Account</p>
                                    </Link>
                                </li>
                                <li>
                                    {auth.isAuthenticated() ?
                                        <a onClick={props.signOut}>
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
                </ul>
            }
            {showSearch &&
                <div className="htm-search">
                    <div className="hs-dropdown header-padding ">Search Entire Site</div>
                    <input type="text" className="hs-input" />
                    <button className="hs-btn">
                        <FontAwesomeIcon icon={faMagnifyingGlass} className="hs-btn-icon" />
                    </button>
                </div>
            }
        </div>
    )
}