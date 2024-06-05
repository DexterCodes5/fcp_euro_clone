import { faAnglesDown, faCircleCheck, faCircleExclamation, faX } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import axios from "axios"
import { AnimatePresence, motion } from "framer-motion"
import { useEffect, useState } from "react"
import { Link, useNavigate, useSearchParams } from "react-router-dom"
import { useLocalStorage } from "../../hooks/useLocalStorage"
import { Errors } from "../../components/Errors/Errors"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"

type Passwords = {
    password: string
    passwordConfirmation: string
}

export const ChangePasswordPage = () => {
    useDocumentTitle("Change Password | FCP Euro")
    const navigate = useNavigate()
    const [searchParams] = useSearchParams()

    const [resetPasswordToken, setResetPasswordToken] = useLocalStorage("reset-password-token", "")

    const [errorNotification, setErrorNotification] = useState("")
    const [errors, setErrors] = useState<string[]>([])

    const [passwords, setPasswords] = useState<Passwords>({ password: "", passwordConfirmation: "" })

    const [isLoading, setIsLoading] = useState(false)

    useEffect(() => {
        if (errorNotification) {
            setTimeout(() => setErrorNotification(""), 5000)
        }
    }, [errorNotification])

    const changePasswords = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setPasswords({ ...passwords, [name]: value })
    }

    const submit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setIsLoading(true)
        const isBlankRegex = /^\s*$/
        if (isBlankRegex.test(passwords.password)) {
            setErrorNotification("Your password cannot be blank.")
            setIsLoading(false)
            return
        }

        let curResetPasswordToken = resetPasswordToken
        const token = searchParams.get("token")
        if (resetPasswordToken === "" || (token && resetPasswordToken !== token)) {
            // check reset password token
            try {
                await axios.get(`${process.env.REACT_APP_API}/api/v1/auth/is-reset-password-token-valid?token=${token}`)
                setResetPasswordToken(token!)
                curResetPasswordToken = token!
            } catch (err) {
                setResetPasswordToken("")
                setErrors(["Reset password token is invalid"])
                return
            } finally {
                navigate("/change-password")
                setIsLoading(false)
            }
        }

        const newErrors = []
        if (passwords.password.length < 6) {
            newErrors.push("Password is too short (must be at least 6 characters).")
        }
        if (passwords.password !== passwords.passwordConfirmation) {
            newErrors.push("Password and Password Confirmation don't match.")
        }
        if (newErrors.length > 0) {
            setErrors(newErrors)
            setIsLoading(false)
            return
        }
        try {
            await axios.post(`${process.env.REACT_APP_API}/api/v1/auth/change-password`, {
                token: curResetPasswordToken,
                password: passwords.password
            })
            localStorage.setItem("notification", "Your password reset was successful")
            navigate("/login")
        } catch (err) {
            setErrorNotification("Something went wrong, please try again later.")
            setIsLoading(false)
        }
    }

    return (
        <div className="login">
            <div className="login-notification">
                <AnimatePresence>
                    {errorNotification &&
                        <motion.div className="auth__notification auth__notificationError"
                            initial={{
                                height: 0,
                                padding: 0
                            }}
                            animate={{
                                height: "auto",
                                padding: ".5rem",
                                transition: { duration: 0 }
                            }}
                            exit={{
                                height: 0,
                                padding: 0
                            }}
                        >
                            <div className="grid-container auth__notificationGrid">
                                <div>
                                    <FontAwesomeIcon icon={faCircleExclamation} className="auth__notification-i" />
                                    {errorNotification}
                                </div>
                                <div>
                                    <FontAwesomeIcon icon={faX} className="auth__notificationClose"
                                        onClick={() => setErrorNotification("")} />
                                </div>
                            </div>
                        </motion.div>
                    }
                </AnimatePresence>
            </div>
            <div className="grid-x">
                <div className="login__left cell small-12 large-6 sticky-container">
                    <div className="login__form sticky is-stuck-large">
                        <div className="login__slide">
                            <div className="login__content">
                                <Link to="/" className="login__homeLink"> &lt; BACK</Link>
                                <div className="auth">
                                    <div className="auth__logo logoBlack"></div>
                                    <div className="auth__password">
                                        <h2>CHANGE MY PASSWORD</h2>
                                        {errors.length > 0 &&
                                            <Errors errors={errors} />
                                        }
                                        <form onSubmit={submit}>
                                            <div className="auth__fields">
                                                <input type="password" placeholder="Password"
                                                    name="password" value={passwords.password}
                                                    onChange={changePasswords} />
                                                <input type="password"
                                                    placeholder="Password Confirmation"
                                                    name="passwordConfirmation"
                                                    value={passwords.passwordConfirmation}
                                                    onChange={changePasswords} />
                                            </div>
                                            <div className="auth__redirects">
                                                Password reset link expired? <a>Request a new one</a>
                                            </div>
                                            <div className="auth__redirects">
                                                This site is protected by reCAPTCHA and the Google <a>Privacy Policy</a> and <a>Terms of Service</a> apply.
                                            </div>
                                            <button className="mainCta mainCta--medium auth__button">
                                                Update
                                            </button>
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