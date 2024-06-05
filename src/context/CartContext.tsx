import { createContext, ReactNode, useContext, useEffect, useState } from "react"
import { useLocalStorage } from "../hooks/useLocalStorage"
import axios from "axios"
import { CartItemModel } from "../model/CartItemModel"

type CartProviderProps = {
    children: ReactNode
}

type CartContext = {
    cartItems: CartItemModel[]
    subtotal: number
    increaseItemQty: (id: number, qty: number) => void
    changeItemQty: (id: number, qty: number) => void
    removeFromCart: (id: number) => void
    emptyCart: () => void
}

const CartContext = createContext({} as CartContext)

export const useCart = () => {
    return useContext(CartContext)
}

export const CartProvider = ({ children }: CartProviderProps) => {
    const [cartItems, setCartItems] = useLocalStorage<CartItemModel[]>("cart-items", [])
    const [subtotal, setSubtotal] = useState(0)

    useEffect(() => {
        const getSubTotal = async () => {
            let subtotal = 0
            for (const cartItem of cartItems) {
                const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/parts/get-part-by-id/${cartItem.id}`)
                subtotal += res.data.price * cartItem.qty
            }
            setSubtotal(Math.round(subtotal * 100) / 100)
        }

        getSubTotal().catch(err => console.error(err))
    }, [cartItems])

    const increaseItemQty = (id: number, qty: number) => {
        let cartItem = cartItems.find(cartItem => cartItem.id === id)
        if (cartItem === undefined) {
            cartItems.push({ id, qty })
        } else {
            cartItem!.qty += qty
        }
        setCartItems([...cartItems])
    }

    const changeItemQty = (id: number, qty: number) => {
        if (qty < 1) {
            throw new Error("Invalid qty: " + qty)
        }
        const cartItem = cartItems.find(cartItem => cartItem.id === id)
        if (cartItem === null) {
            throw Error("Invalid CartItem id: " + id)
        }
        cartItem!.qty = qty
        setCartItems([...cartItems])
    }

    const removeFromCart = (id: number) => {
        setCartItems(curItems => curItems.filter(item => item.id !== id))
    }

    const emptyCart = () => {
        setCartItems([])
    }

    return (
        <CartContext.Provider
            value={{
                cartItems,
                subtotal,
                increaseItemQty,
                changeItemQty,
                removeFromCart,
                emptyCart
            }}
        >
            {children}
        </CartContext.Provider>
    )
}