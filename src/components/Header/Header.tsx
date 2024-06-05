import "./Header.css"
import { HeaderTop } from "./components/HeaderTop/HeaderTop"

export const Header = () => {
    return (
        <header>
            <HeaderTop />
            <div className="header-mid">
                <div className="header-mid-container container">
                    <a href="#" className="header-mid-a">
                        <img src={require("../../images/freeShipping.png")} alt="country" className="free-shipping-img" />
                        <p className="header-mid-p">Free Shipping Over $49</p>
                    </a>
                    <a href="#" className="header-mid-a">
                        <img src={require("../../images/hassleFree.png")} alt="country" className="free-shipping-img" />
                        <p className="header-mid-p">Hassle-Free Returns</p>
                    </a>
                    <a href="#" className="header-mid-a">
                        <img src={require("../../images/lifetime.png")} alt="country" className="free-shipping-img" />
                        <p className="header-mid-p">Lifetime Replacement</p>
                    </a>
                </div>
            </div>
            <div className="header-bottom">
                <ul className="header-bottom-ul container">
                    <li>
                        <a href="#">
                            BMW Parts
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            Volvo Parts
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            VW Parts
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            Audi Parts
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            Mercedes Parts
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            Porsche Parts
                        </a>
                    </li>
                </ul>
            </div>
        </header>
    )
}