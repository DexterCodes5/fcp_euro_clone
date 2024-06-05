import { useParams } from "react-router-dom"
import { VehicleListerAndCategory } from "../../components/VehicleListerAndCategory/VehicleListerAndCategory"
import "./ProductPage.css"
import { useEffect, useRef, useState } from "react"
import axios from "axios"
import { PartModel } from "../../model/PartModel"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faYoutube } from "@fortawesome/free-brands-svg-icons"
import { faHeart, faImages, faThumbsUp, faQuestionCircle as farQuestionCircle } from "@fortawesome/free-regular-svg-icons"
import { faCheck, faQuestionCircle as fasQuestionCircle, faTag, faTruck, faStar as fasStar, faExclamationTriangle } from "@fortawesome/free-solid-svg-icons"
import { Fitment } from "./components/Fitment/Fitment"
import { useLocalStorage } from "../../hooks/useLocalStorage"
import { SavedVehicleModel } from "../../model/SavedVehicleModel"
import { FitmentModel } from "../../model/fitment/FitmentModel"
import { useCart } from "../../context/CartContext"
import { Review } from "./components/Review/Review"
import { ReviewModel } from "../../model/ReviewModel"
import { RatingStars } from "../../components/RatingStars/RatingStars"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"


export const ProductPage = () => {
    const params = useParams()
    const cart = useCart()

    const [product, setProduct] = useState<PartModel>()
    useDocumentTitle(`${product?.title} | FCP Euro`)
    const [qty, setQty] = useState(1)

    const [fits, setFits] = useState("no")

    const [savedVehicles] = useLocalStorage<SavedVehicleModel[]>("saved-vehicles", [])

    const [fitments, setFitments] = useState<FitmentModel[]>()

    const [selectedTab, setSelectedTab] = useState("Description")

    const [reviews, setReviews] = useState<ReviewModel[]>([])
    const [averageRating, setAverageRating] = useState(0)

    const descriptionTab = useRef<HTMLDivElement>(null)
    const fitmentTab = useRef<HTMLDivElement>(null)
    const reviewTab = useRef<HTMLDivElement>(null)

    useEffect(() => {
        const getProduct = async () => {
            try {
                const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/parts/get-part-by-url/${params.product}`)
                setProduct(res.data)
            } catch (err) {
                console.error("Something went wrong")
            }
        }

        getProduct()
    }, [])

    useEffect(() => {
        const doesPartFitSelectedVehicle = async () => {
            if (product === undefined) {
                return
            }

            if (savedVehicles.length === 0) {
                setFits("validate-fitment")
            }

            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/fitment/does-part-fit-vehicle?partId=${product.id}&vehicleId=${savedVehicles[0].vehicle!.id}`)
            if (res.data === true) {
                setFits("yes")
            } else {
                setFits("no")
            }
        }

        doesPartFitSelectedVehicle()
    }, [product])

    useEffect(() => {
        const getFitments = async () => {
            if (product === undefined) {
                return
            }

            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/fitment?partId=${product?.id}`)
            setFitments(res.data)
        }

        getFitments()
    }, [product])

    useEffect(() => {
        const getReviews = async () => {
            if (!product) {
                return
            }
            const res = await axios.get(`${process.env.REACT_APP_API}/api/v1/reviews/${product.id}`)
            setReviews(res.data)
            let allRatingStars = 0
            for (const review of res.data) {
                allRatingStars += review.rating
            }
            const curAverageRating = Math.round((allRatingStars / res.data.length) * 10) / 10
            setAverageRating(curAverageRating)
        }

        getReviews()
    }, [product])

    useEffect(() => {
        const handleScroll = () => {
            if (reviewTab.current?.getBoundingClientRect().top! < 138) {
                setSelectedTab("Reviews")
            }
            else if (fitmentTab.current?.getBoundingClientRect().top! < 138) {
                setSelectedTab("Fitment")
            }
            else {
                setSelectedTab("Description")
            }
        }

        document.addEventListener("scrollend", handleScroll)

        return () => document.removeEventListener("scrollend", handleScroll)
    }, [])

    const oeNumbers: string | undefined = product?.oeNumbers.join(", ")

    const addToCart = () => {
        if (qty < 1) {
            setQty(1)
            return
        }
        cart.increaseItemQty(product?.id!, qty)
    }

    const handleDescription = () => {
        setSelectedTab("Description")
        window.scroll({
            top: (descriptionTab.current?.getBoundingClientRect().top! + window.scrollY) - 190,
            behavior: "smooth"
        })
    }

    const handleFitment = () => {
        setSelectedTab("Fitment")
        window.scroll({
            top: (fitmentTab.current?.getBoundingClientRect().top! + window.scrollY) - 120,
            behavior: "smooth"
        })
    }

    const handleReviews = () => {
        setSelectedTab("Reviews")
        window.scroll({
            top: (reviewTab.current?.getBoundingClientRect().top! + window.scrollY) - 130,
            behavior: "smooth"
        })
    }

    const renderFitmentButton = () => {
        if (fits === "yes") {
            return (
                <div className="fitment__validFit">
                    <FontAwesomeIcon icon={faThumbsUp} className="fitment__validFit-i" /> Yes this part will fit
                </div>
            )
        } else if (fits === "validate-fitment") {
            return (
                <a className="alertButton">
                    <FontAwesomeIcon icon={faExclamationTriangle} /> Validate fitment
                </a>
            )
        } else {
            <a className="alertButton">
                <FontAwesomeIcon icon={faExclamationTriangle} /> This part doesn't fit
            </a>
        }
    }

    return (
        <div className="grid-container">
            <div className="grid-x grid-margin-x">
                <div className="cell small-12 large-3">
                    <VehicleListerAndCategory setRenderPartsPage={null} />
                </div>
                <div className="cell small-12 large-9">
                    <a className="parts-page-banner">
                        <img src={require("../../images/Website_Banner_LIQUIMOLY_Promo_2098x250.webp")} alt="banner" className="parts-page-banner-img" />
                    </a>
                    <div className="listing">
                        <div className="grid-x grid-margin-x">
                            <div className="listing-media">
                                <div className="listing__gallery">
                                    <a className="listing__moreImages">
                                        <FontAwesomeIcon icon={faImages} className="listing__moreImages-i" /> More
                                    </a>
                                    <a className="listing__moreVideos">
                                        <FontAwesomeIcon icon={faYoutube} /> Watch
                                    </a>
                                    <a className="part-img-link">
                                        <img src={product?.img[0]} alt="part" className="product-img" />
                                    </a>
                                    <div className="listing-thumbnails">
                                        {product?.img.slice(1).map(img =>
                                            <a className="listing__thumb part-img-link" key={img}>
                                                <img src={img} alt="part" />
                                            </a>
                                        )}
                                        <a className="listing__thumb listing-video">
                                            <FontAwesomeIcon icon={faYoutube} className="listing-video-i" />
                                            Watch
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div className="listing-specs">
                                <div className="listing-info-row listing-identifier">
                                    <div className="listing-brand">
                                        <img src={product?.brand.img} alt="brand" />
                                    </div>
                                    <div className="listing-sku">
                                        SKU: {product?.sku}
                                    </div>
                                </div>
                                <h1 className="listing__name">{product?.title}</h1>
                                <h2 className="listing__models">{fitments?.map(f => f.model).join(", ")}</h2>
                                <div className="listing-info-row">
                                    <div className="listing-heart">
                                        <a>
                                            <FontAwesomeIcon icon={faHeart} className="listing-heart-i" />
                                            Save for later
                                        </a>
                                    </div>
                                    {reviews.length > 0 &&
                                        <div className="listing-rating">
                                            <RatingStars rating={averageRating} />
                                            <a href={`${process.env.REACT_APP_URL}/products/${params.product}#reviews`}
                                                onClick={handleReviews}>
                                                {reviews.length} Ratings
                                            </a>
                                        </div>
                                    }
                                </div>
                                <hr className="listing-rule"></hr>
                                <div className="listing-info-row listing-tags">
                                    <div className="listing__fulfillment">
                                        <FontAwesomeIcon icon={faCheck} className="listing__fulfillment-i" /> Available
                                    </div>
                                    <div className="listing__fulfillment-desc">
                                        <FontAwesomeIcon icon={faTruck} className="listing__fulfillment-desc-i" /> In Stock
                                    </div>
                                    <div className="listing__sale-type">
                                        <FontAwesomeIcon icon={faTag} className="listing__sale-type-i" /> Discount
                                    </div>
                                </div>
                                <div className="listing__price">
                                    Price each: <span>${product?.price}</span>
                                </div>
                                <div className="grid-x grid-margin-x grid-margin-y align-middle">
                                    <div className="cell small-12 medium-6">
                                        <div className="listing__input-options">
                                            <div className="listing__quantity">
                                                QTY
                                                <input type="number" min={1} value={qty}
                                                    onChange={e => setQty(Number(e.target.value))} />
                                            </div>
                                        </div>
                                    </div>
                                    <div className="cell small-12 medium-6">
                                        <div className="add-to-cart-btn" onClick={addToCart}>Add to cart</div>
                                    </div>
                                    <div className="cell small-12 medium-6">
                                        <div className="fitment__notification">
                                            <FontAwesomeIcon icon={farQuestionCircle} className="fitment__notification-i" /> Make sure it first your car
                                        </div>
                                    </div>
                                    <div className="cell small-12 medium-6">
                                        {renderFitmentButton()}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="extended grid-x grid-margin-x">
                        <div className="extended__pages">
                            <ul className={`extended__tabs tabs sticky`}>
                                <li className={`tabs-title${selectedTab === "Description" ? " is-active" : ""}`}>
                                    <a onClick={handleDescription}>Description</a>
                                </li>
                                <li className={`tabs-title${selectedTab === "Fitment" ? " is-active" : ""}`}>
                                    <a onClick={handleFitment}>Fitment</a>
                                </li>
                                <li className={`tabs-title${selectedTab === "Reviews" ? " is-active" : ""}`}>
                                    <a onClick={handleReviews}>Reviews</a>
                                </li>
                                <li className="tabs-title">
                                    <a>Q&A</a>
                                </li>
                                <li className="tabs-title">
                                    <a>Warranty</a>
                                </li>
                            </ul>
                            <div className="extended__content tabs-content">
                                <div className="tabs-panel" ref={descriptionTab}>
                                    <h2>Description</h2>
                                    <div className="extended__specification">
                                        <div className="grid-x grid-margin-x grid-margin-y">
                                            <div className="cell small-12 medium-4">
                                                <div className="extended__details">
                                                    <h3>Details</h3>
                                                    <dl>
                                                        <dt>SKU:</dt>
                                                        <dd>{product?.sku}</dd>
                                                        <dt>FCP Euro ID:</dt>
                                                        <dd>{product?.fcpEuroId}</dd>
                                                        {product?.madeIn &&
                                                            <>
                                                                <dt>Made in:</dt>
                                                                <dd>
                                                                    {product?.madeIn}&nbsp;
                                                                    <span title="We do our best to keep this information updated and accurate, but we cannot guarantee Country of Origin as manufacturers may permanently change their location without any notice.">
                                                                        <FontAwesomeIcon icon={fasQuestionCircle} />
                                                                    </span>
                                                                </dd>
                                                            </>
                                                        }
                                                        <dt>Quality:</dt>
                                                        <dd>
                                                            {product?.quality}&nbsp;
                                                            <span title="This is the original part used by the vehicle manufacturer on the car when it left the factory and FCP recommends OE replacement as one of the best options for quality and price. The vehicle manufacturers (BMW, Volvo, etc.) do not manufacture many products themselves but rather they contract their Original Equipment Manufacturers (OEM’s) to do so. The products that we list as OE are guaranteed to be the exact same part that was on your car when it left the assembly line. In some cases they are even better quality than what is currently available in Genuine because over time some Genuine contracts get changed to save money. All of these parts will be stamped with the OEM’s brand name such as Bosch, Hella, or Lemforder but due to contractual agreements the vehicle manufacturer’s logo cannot be on the product and may be removed from the part.">
                                                                <FontAwesomeIcon icon={fasQuestionCircle} />
                                                            </span>
                                                        </dd>
                                                    </dl>
                                                </div>
                                            </div>
                                            <div className="cell small-12 medium-4">
                                                <div className="extended__oeNumbers">
                                                    <h3>OE Numbers</h3>
                                                    <p>{oeNumbers ? oeNumbers : "N/A"}</p>
                                                </div>
                                            </div>
                                            <div className="cell small-12 medium-4">
                                                <div className="extended__mfgNumbers">
                                                    <h3>MFG Numbers</h3>
                                                    <p>{product?.mfgNumbers.join(", ")}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    {product && product?.kitParts.length > 0 &&
                                        <div className="extended__kit">
                                            <h3>This Kit Includes</h3>
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <th>Qty</th>
                                                        <th>Part</th>
                                                        <th>SKU</th>
                                                        <th>Brand</th>
                                                        <th>Price</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    {product.kitParts.map(kitPart =>
                                                        <tr key={kitPart.part.id}>
                                                            <td>{kitPart.quantity}</td>
                                                            <td>
                                                                <a href={`${process.env.REACT_APP_URL}/products/${kitPart.part.url}`}>{kitPart.part.categoryBot}</a>
                                                            </td>
                                                            <td>{kitPart.part.sku}</td>
                                                            <td>
                                                                <img src={kitPart.part.brand.img} alt="brand" />
                                                            </td>
                                                            <td>{Math.round((kitPart.part.price * kitPart.quantity) * 100) / 100}</td>
                                                        </tr>
                                                    )}
                                                </tbody>
                                            </table>
                                        </div>
                                    }
                                    <div className="extended__moreInfo">
                                        <h3>Product Information</h3>
                                        {product &&
                                            <>
                                                <div className="extended__partInfo" dangerouslySetInnerHTML={{ __html: product?.productInformationHtml }}></div>
                                                <div className="extended__brandInfo"
                                                    dangerouslySetInnerHTML={{
                                                        __html: product.brand.aboutHtml
                                                    }}></div>
                                            </>
                                        }
                                    </div>
                                </div>
                                {fitments &&
                                    <Fitment fitments={fitments} partCategory={product?.categoryBot!}
                                        fitmentTab={fitmentTab}
                                    />
                                }
                                {product &&
                                    <Review reviewTab={reviewTab} reviews={reviews}
                                        averageRating={averageRating} />
                                }
                            </div>
                        </div>
                    </div>
                    <div className="product-page-margin-bottom"></div>
                </div>
            </div>
        </div>
    )
}