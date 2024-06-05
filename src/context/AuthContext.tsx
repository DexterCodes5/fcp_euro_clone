import axios from "axios"
import { createContext, ReactNode, useContext, useEffect, useState } from "react"
import { AuthRequest } from "../model/AuthRequest"
import { UserModel } from "../model/UserModel"

type AuthProviderProps = {
    children: ReactNode
}

type RegisterRequest = {
    email: string,
    username: string,
    password: string
}

type AuthContext = {
    register: (registerRequest: RegisterRequest) => Promise<void>
    verifyEmail: (token: string | null) => Promise<void>
    authenticate: (authRequest: AuthRequest, recaptchaToken: string) => Promise<void>
    logout: () => Promise<void>
    getAccessToken: () => Promise<string | null>
    isAuthenticated: () => boolean
    getUser: () => Promise<UserModel>
    refresh: () => Promise<string | null>
    isLoading: boolean
}

const AuthContext = createContext({} as AuthContext)

export const useAuth = () => {
    return useContext(AuthContext)
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
    const [accessToken, setAccessToken] = useState("")
    const [isLoading, setIsLoading] = useState(true)

    useEffect(() => {
        const init = async () => {
            await refresh()
            setIsLoading(false)
        }

        init()
    }, [])

    const refresh = async () => {
        const refreshToken = localStorage.getItem("refresh-token")
        if (!refreshToken) {
            localStorage.setItem("refresh-token", "")
            setAccessToken("")
            return null
        }
        try {
            const res = await axios.post(`${process.env.REACT_APP_API}/api/v1/auth/refresh-token`, null, {
                headers: {
                    Authorization: `Bearer ${refreshToken}`
                }
            })
            if (!res.data.accessToken) {
                throw Error("Refresh Token failed")
            }
            localStorage.setItem("refresh-token", res.data.refreshToken)
            setAccessToken(res.data.accessToken)
            return res.data.accessToken
        } catch (err) {
            localStorage.setItem("refresh-token", "")
            setAccessToken("")
            return null
        }
    }

    const register = async (
        registerRequest: RegisterRequest
    ) => {
        await axios.post(`${process.env.REACT_APP_API}/api/v1/auth/register`, registerRequest)
    }

    const verifyEmail = async (token: string | null) => {
        if (!token) {
            throw Error("Invalid token")
        }
        await axios.post(`${process.env.REACT_APP_API}/api/v1/auth/verify-email?token=${token}`)
    }

    const authenticate = async (authRequest: AuthRequest, recaptchToken: string) => {
        const res = await axios.post(`${process.env.REACT_APP_API}/api/v1/auth/authenticate`, authRequest,
            {
                headers: {
                    "recaptcha": recaptchToken
                }
            }
        )
        localStorage.setItem("refresh-token", res.data.refreshToken)
        setAccessToken(res.data.accessToken)
    }

    const getAccessToken = async () => {
        const accessToken = await refresh()
        return accessToken
    }

    const logout = async () => {
        const accessToken = await getAccessToken()
        try {
            await axios.post(`${process.env.REACT_APP_API}/api/v1/auth/logout`, null, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
        } catch (err) {
            console.error("Logout failed")
        }
        localStorage.setItem("refresh-token", "")
        setAccessToken("")
    }

    // This function is used for rendering
    const isAuthenticated = () => {
        return accessToken !== ""
    }

    const getUser = async () => {
        const accessToken = await getAccessToken()
        if (accessToken === null) {
            throw Error("Unauthenticated")
        }
        const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/auth/get-user`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
        return res.data
    }

    return (
        <AuthContext.Provider
            value={{
                register,
                verifyEmail,
                authenticate,
                logout,
                getAccessToken,
                isAuthenticated,
                getUser,
                refresh,
                isLoading
            }}
        >
            {children}
        </AuthContext.Provider>
    )
} 