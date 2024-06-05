import { Link, useNavigate } from "react-router-dom"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faAnglesDown, faCircleCheck, faX } from "@fortawesome/free-solid-svg-icons"
import { useEffect, useState } from "react"
import axios from "axios"
import { AnimatePresence, motion } from "framer-motion"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"

export const ForgotPasswordPage = () => {
    useDocumentTitle("Reset You Password | FCP Euro")
    const navigate = useNavigate()

    const [email, setEmail] = useState("")

    const [notificationError, setNotificationError] = useState("")

    const [isLoading, setIsLoading] = useState(false)

    useEffect(() => {
        if (notificationError) {
            setTimeout(() => setNotificationError(""), 5000)
        }
    }, [notificationError])

    const resetPassword = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setIsLoading(true)
        try {
            await axios.post(`${process.env.REACT_APP_API}/api/v1/auth/forgot-password?email=${email}`)
            navigate("/login")
            localStorage.setItem("notification", "If an account with that email address exists, you will receive an email with instructions about how to reset your password in a few minutes.")
        } catch (err) {
            setNotificationError("Something went wrong, please try again later.")
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <div className="login">
            <AnimatePresence>
                {notificationError &&
                    <motion.div className="auth__notification auth__notificationError"
                        initial={{
                            height: 0,
                            padding: 0
                        }}
                        animate={{
                            height: "auto",
                            padding: "1rem"
                        }}
                        exit={{
                            height: 0,
                            padding: 0
                        }}
                    >
                        <div className="grid-container auth__notificationGrid">
                            <div>
                                <FontAwesomeIcon icon={faCircleCheck} className="auth__notification-i" />
                                {notificationError}
                            </div>
                            <div>
                                <FontAwesomeIcon icon={faX} className="auth__notificationClose"
                                    onClick={() => setNotificationError("")} />
                            </div>
                        </div>
                    </motion.div>
                }
            </AnimatePresence>
            <div className="grid-x">
                <div className="login__left cell small-12 large-6 sticky-container">
                    <div className="login__form sticky is-stuck-large">
                        <div className="login__slide">
                            <div className="login__content">
                                <a className="login__homeLink" onClick={() => navigate(-1)}> &lt; BACK</a>
                                <div className="auth">
                                    <div className="auth__logo logoBlack"></div>
                                    <div className="auth__password">
                                        <h2>RESET YOUR PASSWORD</h2>
                                        <form onSubmit={resetPassword}>
                                            <div className="auth__fields">
                                                <input type="email" placeholder="Your email address"
                                                    value={email} onChange={e => setEmail(e.target.value)} />
                                            </div>
                                            <div className="auth__redirects">
                                                This site is protected by reCAPTCHA and the Google <a>Privacy Policy</a> and <a>Terms of Service</a> apply.
                                            </div>
                                            <button className={`mainCta mainCta--medium auth__button
                                            ${isLoading ? " isLoading": ""}`}>Reset</button>
                                        </form>
                                    </div>
                                </div>
                                <div className="login__arrowIndicator login--mobileIndicator show-for-small hide-for-large">
                                    <FontAwesomeIcon icon={faAnglesDown} className="login__arrowIndicator-i" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="cell small-12 large-6">
                    <div className="login__slides">
                        <div className="login__explore">
                            <div className="login__slide">
                                <div className="login__qualities">GENUINE / OE / OEM / AFTERMARKET / PERFORMANCE</div>
                                <h2>EUROPEAN CAR PARTS</h2>
                                <p>Our online parts catalog features over 190,000 Genuine, OE, and OEM replacement parts including <strong>Volvo Parts, BMW Parts, Audi Parts, VW Parts, Mercedes Parts, Porsche Parts</strong> and <strong>Saab Parts</strong> from a wide variety of top-quality manufacturers.</p>
                                <div className="login__arrowIndicator show-for-large">
                                    <FontAwesomeIcon icon={faAnglesDown} className="login__arrowIndicator-i" />
                                </div>
                            </div>
                        </div>
                        <div className="login__lifetime">
                            <div className="login__slide">
                                <img src={require("../../images/LRG.webp")} alt="Lrg" />
                                <h3>Buy it. Use it. Replace it.</h3>
                                <p>Every part you buy from FCP Euro is guaranteed for life, for as long as you own the vehicle. Our Lifetime Replacement Guarantee covers everything we sell, including wear-and-tear items like brake pads, gaskets, brake rotors, filters, and wiper blades!</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}