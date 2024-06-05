import { jwtDecode } from "jwt-decode"

export const isTokenExpired = (token: string) => {
    if (!token) {
        return true
    }

    try {
        const decodedToken = jwtDecode(token)
        return decodedToken.exp! * 1000 < Date.now()
    } catch (err) {
        return true
    }
}