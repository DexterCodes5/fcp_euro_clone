import { motion } from "framer-motion"
import { useState } from "react"
import { useAuth } from "../../../context/AuthContext"
import { Errors } from "../../../components/Errors/Errors"

type RegisterData = {
    email: string,
    username: string,
    password: string,
    confirmPassword: string
}

export const Register: React.FC<{
    setLogin: React.Dispatch<React.SetStateAction<boolean>>,
    setAuthFailedNotification: React.Dispatch<React.SetStateAction<string>>,
    setIsLoading: React.Dispatch<React.SetStateAction<boolean>>
}> = (props) => {
    const auth = useAuth()

    const [registerData, setRegisterData] = useState<RegisterData>({
        email: "",
        username: "",
        password: "",
        confirmPassword: ""
    })

    const [registerSuccessNotification, setRegisterSuccessNotification] = useState("")

    const [errors, setErrors] = useState<string[]>([])

    const changeRegisterData = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setRegisterData({ ...registerData, [name]: value })
    }

    const signUp = async () => {
        setErrors([])
        setRegisterSuccessNotification("")
        props.setIsLoading(true)
        if (registerData.password !== registerData.confirmPassword) {
            props.setAuthFailedNotification("Passwords don't match")
            props.setIsLoading(false)
            return
        }
        try {
            await auth.register({
                email: registerData.email,
                username: registerData.username,
                password: registerData.password
            })
            setRegisterSuccessNotification(registerData.email)
            setRegisterData({
                email: "",
                username: "",
                password: "",
                confirmPassword: ""
            })
        } catch (err: any) {
            console.log(err)
            if (err.response.status === 400) {
                const message = err.response.data.message
                setErrors(message.replaceAll(/[{}]/g, "").split(","))
            } else {
                props.setAuthFailedNotification("Something went wrong, please try again later.")
            }
        } finally {
            props.setIsLoading(false)
        }
    }

    return (
        <motion.div className="auth__signup"
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
            <h1>CREATE<br />AN ACCOUNT</h1>
            <p className="auth__subText">Sign up for an FCP Euro account and enjoy tons of member perks designed to create a more rewarding european car ownership experience.</p>
            {errors.length > 0 &&
                <Errors errors={errors} />
            }
            <div className="fieldGroup">
                <div className="fieldGroup__row">
                    <input type="email" placeholder="Email address" name="email" value={registerData.email}
                        onChange={changeRegisterData} />
                </div>
                <div className="fieldGroup__row">
                    <input type="text" placeholder="Display Name" name="username" value={registerData.username}
                        onChange={changeRegisterData} />
                </div>
                <div className="fieldGroup__row">
                    <input type="password" placeholder="Password" name="password" value={registerData.password}
                        onChange={changeRegisterData} />
                </div>
                <div className="fieldGroup__row">
                    <input type="password" placeholder="Confirm password" name="confirmPassword"
                        value={registerData.confirmPassword} onChange={changeRegisterData} />
                </div>
            </div>
            <div className="auth__checkbox">
                <div className="checkbox">
                    <input type="checkbox" id="user_remember_me" />
                    <label htmlFor="user_remember_me">
                        Please send me my reward and promotional emails
                    </label>
                </div>
            </div>
            {registerSuccessNotification &&
                <div className="register-notification">
                    We sent a verification email to {registerSuccessNotification}. Please verify your email.
                </div>
            }
            <button className="mainCta mainCta--medium" onClick={signUp}>Sign up</button>
            <div className="auth__redirects">Already a member? <a onClick={() => props.setLogin(true)}>Login here</a></div>
        </motion.div>
    )
}