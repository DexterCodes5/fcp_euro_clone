import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import "./Footer.css"
import { faEnvelope } from "@fortawesome/free-solid-svg-icons"
import { faFacebook, faInstagram, faYoutube } from "@fortawesome/free-brands-svg-icons"

export const Footer = () => {
    return (
        <footer className="footer">
            <div className="container footer-content-padding">
                <div className="footer-content">
                    <div className="footer-content-cell">
                        <img src={require("../../images/fcpEuroWhite.png")} alt="logo" className="footer__logo" />
                        <p className="contact-p">Get help answering any questions you have</p>
                        <a className="contact-btn">
                            Contact Us
                            <FontAwesomeIcon icon={faEnvelope} />
                        </a>
                        <div className="footer-social">
                            <a className="social-a social-f-a">
                                <FontAwesomeIcon icon={faFacebook} className="social" />
                            </a>
                            <a className="social-a social-i-a">
                                <FontAwesomeIcon icon={faInstagram} className="social " />
                            </a>
                            <a className="social-a social-y-a">
                                <FontAwesomeIcon icon={faYoutube} className="social" />
                            </a>
                        </div>
                    </div>
                    <div className="cell-f">
                        <p className="footer-customer-service-p">Customer Service</p>
                        <ul className="footer-customer-service-ul">
                            <li>
                                <a>Contact us</a>
                            </li>
                            <li>
                                <a>Order Status & Tracking</a>
                            </li>
                            <li>
                                <a>Returns</a>
                            </li>
                            <li>
                                <a>Lifetime Replacement</a>
                            </li>
                            <li>
                                <a>Gurantee</a>
                            </li>
                            <li>
                                <a>Shipping Policy</a>
                            </li>
                            <li>
                                <a>Deals & Coupons</a>
                            </li>
                            <li>
                                <a>FAQ</a>
                            </li>
                        </ul>
                    </div>
                    <div className="cell-f f-small-4">
                        <p className="footer-customer-service-p">About FCP Euro</p>
                        <ul className="footer-customer-service-ul">
                            <li>
                                <a>About us</a>
                            </li>
                            <li>
                                <a>Privacy Policy</a>
                            </li>
                            <li>
                                <a>DYI Blog</a>
                            </li>
                            <li>
                                <a>Motorsports Program</a>
                            </li>
                            <li>
                                <a>OE Acadamy</a>
                            </li>
                            <li>
                                <a>We're Hiring!</a>
                            </li>
                            <li>
                                <a>Teams of Service</a>
                            </li>
                        </ul>
                    </div>
                    <div className="footer-content-cell f-small-3">
                        <div className="sonp">
                            <FontAwesomeIcon icon={faEnvelope} />
                            Special offers, news & promos
                        </div>
                        <form className="footer-sign-up">
                            <input type="email" className="footer-sign-up-input" />
                            <button className="footer-sign-up-btn">Sign up</button>
                        </form>
                        <table className="footer-ratings-table">
                            <tbody>
                                <tr>
                                    <td>
                                        <img src={require("../../images/googleReviews.png")} alt="google-reviews" className="footer-rating-img" />
                                    </td>
                                    <td>
                                        <img src={require("../../images/trustPilot.png")} alt="google-reviews" className="footer-rating-img-trustpilot" />
                                    </td>
                                    <td>
                                        <img src={require("../../images/yelp.png")} alt="google-reviews" className="footer-rating-img-yelp" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        4.8/5
                                    </td>
                                    <td>
                                        4.7/5
                                    </td>
                                    <td>
                                        4.0/5
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div className="footer-copyright">
                &copy; FCP Euro 2024. All rights reserved. Version: c2a745ee0
            </div>
        </footer>
    )
}