import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import "../../css/Login.css"
import { faAnglesDown, faCircleExclamation, faX } from "@fortawesome/free-solid-svg-icons"
import { useEffect, useState } from "react"
import { Register } from "./Register/Register"
import { AnimatePresence, motion } from "framer-motion"
import { Link, useLocation, useNavigate } from "react-router-dom"
import { useAuth } from "../../context/AuthContext"
import { AuthRequest } from "../../model/AuthRequest"
import { SpinnerLoading } from "../../components/SpinnerLoading/SpinnerLoading"
import { Notification } from "../../components/Notification/Notification"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"
import { GoogleReCaptcha, GoogleReCaptchaProvider } from "react-google-recaptcha-v3"

export const LoginPage = () => {
    useDocumentTitle("Returning Customer Login | FCP Euro")
    const auth = useAuth()
    const navigate = useNavigate()
    const location = useLocation()
    
    const [isLoading, setIsLoading] = useState(false)

    const [login, setLogin] = useState(true)

    const [authRequest, setAuthRequest] = useState<AuthRequest>(new AuthRequest("", "", false))

    const [authFailedNotificaiton, setAuthFailedNotification] = useState("")

    const [recaptchaToken, setRecaptchaToken] = useState("")
    const [refreshRecaptcha, setRefreshRecaptcha] = useState(false)

    useEffect(() => {
        if (!auth.isLoading && auth.isAuthenticated()) {
            localStorage.setItem("alert-notification", "You are already signed in.")
            loginNavigate()
        }
    }, [auth.isLoading])

    useEffect(() => {
        if (authFailedNotificaiton) {
            setTimeout(() => setAuthFailedNotification(""), 5000)
        }
    }, [authFailedNotificaiton])

    const loginNavigate = () => {
        if (location.state && location.state.from) {
            navigate(location.state.from)
        } else {
            navigate("/")
        }
    }

    const changeAuthRequest = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setAuthRequest({ ...authRequest, [name]: value })
    }

    const authenticate = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        try {
            setIsLoading(true)
            await auth.authenticate(authRequest, recaptchaToken)
            loginNavigate()
        } catch (err: any) {
            setAuthFailedNotification("Username and password are incorrect.")
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <div className="login">
            {isLoading &&
                <SpinnerLoading />
            }
            <Notification />
            <AnimatePresence>
                {authFailedNotificaiton &&
                    <motion.div className="notification notification--error"
                        initial={{
                            height: 0,
                            padding: 0
                        }}
                        animate={{
                            height: "auto",
                            padding: ".5rem"
                        }}
                        exit={{
                            height: 0,
                            padding: 0
                        }}
                    >
                        <div className="grid-container">
                            <div className="notification__container">
                                <div className="notification__message">
                                    <FontAwesomeIcon icon={faCircleExclamation} className="notification__i" />
                                    {authFailedNotificaiton}
                                </div>
                                <div className="notification__close">
                                    <FontAwesomeIcon icon={faX} className="notification__closeI"
                                        onClick={() => setAuthFailedNotification("")} />
                                </div>
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
                                    <AnimatePresence initial={false}>
                                        {login &&
                                            <motion.div className="auth__login"
                                                initial={{
                                                    height: 0
                                                }}
                                                animate={{
                                                    height: "auto"
                                                }}
                                                exit={{
                                                    height: 0
                                                }}
                                                transition={{
                                                    duration: .5
                                                }}
                                            >
                                                <h1>SIGN IN TO BEGIN</h1>
                                                <form onSubmit={authenticate}>
                                                    <div className="fieldGroup">
                                                        <div className="fieldGroup__row">
                                                            <input type="email" placeholder="Email address" name="email"
                                                                value={authRequest.email} onChange={changeAuthRequest} />
                                                        </div>
                                                        <div className="fieldGroup__row">
                                                            <input type="password" placeholder="Password" name="password"
                                                                value={authRequest.password} onChange={changeAuthRequest} />
                                                        </div>
                                                    </div>
                                                    <div className="auth__redirects">
                                                        This site is protected by reCAPTCHA and the Google <a>Privacy Policy</a> and <a>Terms of Service</a> apply.
                                                    </div>
                                                    <GoogleReCaptchaProvider reCaptchaKey={process.env.REACT_APP_RECAPTCHA_KEY!}>
                                                        <GoogleReCaptcha 
                                                        onVerify={setRecaptchaToken}
                                                        refreshReCaptcha={refreshRecaptcha}
                                                        />
                                                    </GoogleReCaptchaProvider>
                                                    <div className="auth__checkbox">
                                                        <div className="checkbox">
                                                            <input type="checkbox" id="user_remember_me"
                                                            checked={authRequest.rememberMe}
                                                            name="rememberMe" onChange={e => setAuthRequest({...authRequest, rememberMe: e.target.checked})} />
                                                            <label htmlFor="user_remember_me">
                                                                Remember me
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div className="auth__redirects">
                                                        Forgot your password? <Link to="/password/recover">Reset here</Link>
                                                    </div>
                                                    <button className="mainCta mainCta--medium auth__button">
                                                        Login
                                                    </button>
                                                    <div className="auth__redirects">
                                                        Not an FCP Euro customer? <a onClick={() => setLogin(false)}>Register now</a>
                                                    </div>
                                                </form>
                                            </motion.div>
                                        }
                                    </AnimatePresence>
                                    <AnimatePresence>
                                        {!login &&
                                            <Register setLogin={setLogin}
                                                setAuthFailedNotification={setAuthFailedNotification}
                                                setIsLoading={setIsLoading}
                                            />
                                        }
                                    </AnimatePresence>
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