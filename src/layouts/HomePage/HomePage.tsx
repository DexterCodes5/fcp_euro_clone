import { VehicleListerAndCategory } from "../../components/VehicleListerAndCategory/VehicleListerAndCategory"
import { useAuth } from "../../context/AuthContext"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"
import "./HomePage.css"

export const HomePage = () => {
    useDocumentTitle("Genuine and OEM Replacement Car Parts Online | FCP Euro")
    const auth = useAuth()
    
    return (
        <div className="container">
            <div className="grid">
                <div className="grid-left">
                    <VehicleListerAndCategory setRenderPartsPage={null} />
                    <div className="homepage-sidebar-imgs">
                        <a className="homepage-sidebar-img-link">
                            <img src={require("../../images/homepageSidebarImage.jpg")} alt="img" className="homepage-sidebar-img" />
                        </a>
                        <a className="homepage-sidebar-img-link">
                            <img src={require("../../images/Hiring-homepage-sidebar-2.webp")} alt="img" className="homepage-sidebar-img" />
                        </a>
                        <a className="homepage-sidebar-img-link">
                            <img src={require("../../images/OE-academy-sidebar-Q3-2020.webp")} alt="img" className="homepage-sidebar-img" />
                        </a>
                    </div>
                </div>
                <div className="grid-right">
                    <div className="hero">
                        <img src={require("../../images/Website_Header_LIQUIMOLY_Promo_C_2098x1022.webp")} alt="img" className="hero-img" />
                    </div>
                    <button className="primaryButton"onClick={() => console.log(auth.isAuthenticated())}>isAuthenticated</button>
                </div>
            </div>
        </div>
    )
}