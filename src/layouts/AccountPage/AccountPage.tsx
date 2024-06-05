import { useEffect } from "react"
import { useAuth } from "../../context/AuthContext"
import { useNavigate } from "react-router-dom"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"

export const AccountPage = () => {
    useDocumentTitle("My Account | FCP Euro")
    const auth = useAuth()
    const navigate = useNavigate()

    useEffect(() => {
        if (!auth.isAuthenticated()) {
            navigate("/login")
        }
    }, [])

    return (
        <div className="grid-container">
            <h1>Account</h1>
        </div>
    )
}