import { Navigate, useLocation, useNavigate } from "react-router-dom"
import { useAuth } from "../../context/AuthContext"
import { useEffect } from "react"

interface AuthGuardProps {
    component: React.ComponentType<any>
    [key: string]: any; // This allows passing additional props
}

export const AuthGuard: React.FC<AuthGuardProps> = ({ component, ...props }) => {
    const auth = useAuth()
    const location = useLocation()
    const navigate = useNavigate()
    
    useEffect(() => {
        if (!auth.isLoading && !auth.isAuthenticated()) {
            localStorage.setItem("notification", "You must be signed in to do that")
            navigate("/login", { state: { from: location }})
        }
    }, [auth.isLoading])

    const Component = component

    return <Component {...props} />
}