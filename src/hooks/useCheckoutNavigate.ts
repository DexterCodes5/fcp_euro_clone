import { useEffect } from "react"
import { useAuth } from "../context/AuthContext"
import { AddressModel } from "../model/checkout/AddressModel"
import { DeliveryModel } from "../model/checkout/DeliveryModel"
import { useLocalStorage } from "./useLocalStorage"
import { useNavigate } from "react-router-dom"

export const useCheckoutNavigate = (page: string) => {
    const auth = useAuth()
    const navigate = useNavigate()
    const [guestEmail] = useLocalStorage<string>("guest-email", "")
    const [address] = useLocalStorage<AddressModel | null>("address", null)
    const [delivery] = useLocalStorage<DeliveryModel | null>("delivery", null)

    useEffect(() => {
        if (!auth.isLoading) {
            if (auth.isAuthenticated() || guestEmail) {
                if (page === "delivery") {
                    if (!address) {
                        navigate("/checkout/address")
                    }
                } else if (page === "payment") {
                    if (!delivery) {
                        navigate("/checkout/delivery")
                    }
                }
            } else {
                navigate("/checkout/address")
            }
        }
    }, [auth.isLoading])
}