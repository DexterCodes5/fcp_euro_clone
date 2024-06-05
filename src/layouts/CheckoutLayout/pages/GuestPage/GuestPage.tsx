import { Link } from "react-router-dom"
import { OrderSummary } from "../../../../components/OrderSummary/OrderSummary"
import "../../../../css/Checkout.css"
import { useDocumentTitle } from "../../../../hooks/useDocumentTitle"
import "./GuestPage.css"
import { useState } from "react"

export const GuestPage: React.FC<{
    setGuestEmail: React.Dispatch<React.SetStateAction<string>>
}> = (props) => {
    useDocumentTitle("Have you been here before? | FCP Euro")

    const [email, setEmail] = useState("")

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        props.setGuestEmail(email)
    }

    return (
        <div className="guest-page">
            <div className="checkout__container grid-container">
                <div className="checkout__panes">
                    <div className="checkout__leftPane">
                        <form className="guest-page-form" onSubmit={handleSubmit}>
                            <h1 className="checkout__title">Continue as guest</h1>
                            <input type="email" placeholder="Guest Email" required value={email}
                                onChange={e => setEmail(e.target.value)} />
                            <input type="submit" value="Continue" className="secondaryCta" />
                        </form>
                        <div className="guestPage__signIn">
                            <h2>Or proceed with you accont. <Link to="/login" className="guestPage__link">Sign in</Link></h2>
                        </div>
                    </div>
                    <div className="checkout__rightPane">
                        <OrderSummary tax={0} Button={null} />
                    </div>
                </div>
            </div>
        </div>
    )
}