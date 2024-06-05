import { useEffect } from "react"
import { useNavigate, useSearchParams } from "react-router-dom"
import { useAuth } from "../../context/AuthContext"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"

export const VerifyEmail = () => {
    useDocumentTitle("Email Verificaion | FCP Euro")
    const [searchParams] = useSearchParams()
    const navigate = useNavigate()
    const auth = useAuth()

    useEffect(() => {
        const verifyEmail = async () => {
            const token = searchParams.get("token")
            try {
                await auth.verifyEmail(token)
                navigate("/?email-verified=true")
                localStorage.setItem("notification", "You have successfully verified your email.")
            } catch (err: any) {
                navigate("/?email-verified=false")
                localStorage.setItem("error-notification", "There was an error during email verification. Please try again later.")
            }
        }

        verifyEmail()
    }, [])

    return null
}