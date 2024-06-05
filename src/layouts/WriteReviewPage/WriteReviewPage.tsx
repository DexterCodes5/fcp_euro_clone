import axios from "axios"
import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import { PartModel } from "../../model/PartModel"
import "./WriteReviewPage.css"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faStar as farStar } from "@fortawesome/free-regular-svg-icons"
import { faStar as fasStar } from "@fortawesome/free-solid-svg-icons"
import { ReviewModel } from "../../model/ReviewModel"
import { useAuth } from "../../context/AuthContext"
import { Errors } from "../../components/Errors/Errors"
import { useDocumentTitle } from "../../hooks/useDocumentTitle"

export const WriteReviewPage = () => {
    const params = useParams()
    const auth = useAuth()
    const navigate = useNavigate()

    const [product, setProduct] = useState<PartModel>()
    useDocumentTitle(`Write a Review for ${product?.title} | FCP Euro`)

    const [fullStars, setFullStars] = useState(0)

    const [review, setReview] = useState<ReviewModel>(new ReviewModel(0, "", "", 0, "", ""))

    const [errors, setErrors] = useState<string[]>([])

    useEffect(() => {
        const initialize = async () => {
            let res
            try {
                res = await axios.get(`${process.env.REACT_APP_API}/api/v1/parts/get-part-by-url/${params.product}`)
                setProduct(res.data)
                setReview({ ...review, partId: res.data.id })
            } catch (err) {
                console.error("getProduct failed")
                return
            }
        }

        initialize()
    }, [])

    const changeReview = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target
        setReview({ ...review!, [name]: value })
    }

    const submit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        const accessToken = await auth.getAccessToken()
        if (accessToken === null) {
            navigate(`/products/${product?.url}/reviews`)
        }

        if (review.rating <= 0) {
            setErrors(["Rating must be selected."])
            return
        }

        try {
            await axios.post(`${process.env.REACT_APP_API}/api/v1/reviews`, review, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
            navigate(`/products/${product!.url}`)
        } catch (err: any) {
            if (err.response.status === 400) {
                const newErrors = err.response.data.message.replaceAll(/[{}]/g, "").split(",")
                setErrors(newErrors)
            } else {
                setErrors(["Something went wrong, please try again later."])
            }
        }
    }

    return (
        <div className="productForm">
            <div className="productForm__head">
                <div className="grid-container">
                    <div className="grid-x">
                        <div className="cell small-12 large-8">
                            <div className="productForm__fauxh">
                                What did you think of the
                                <strong className="productForm__hstrong">{product?.title}?</strong>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="productForm__body">
                <div className="grid-container">
                    <div className="grid-x grid-margin-x productForm__pulltop">
                        <div className="cell small-12 large-4 large-offset-8">
                            <div className="productForm__image">
                                <img src={product?.img[0]} alt="product" />
                            </div>
                        </div>
                    </div>
                    <form className="new_review" onSubmit={submit}>
                        <div className="grid-x grid-margin-x">
                            <div className="cell">
                                {errors.length > 0 &&
                                    <Errors errors={errors} />
                                }
                            </div>
                            <div className="cell small-12">
                                <div className="productForm__stars">
                                    <li onMouseEnter={() => setFullStars(1)}
                                        onMouseLeave={() => setFullStars(review!.rating)}
                                        onClick={() => setReview({ ...review!, rating: 1 })}>
                                        <FontAwesomeIcon icon={fullStars >= 1 ? fasStar : farStar} className="productForm__star" />
                                    </li>
                                    <li onMouseEnter={() => setFullStars(2)}
                                        onMouseLeave={() => setFullStars(review!.rating)}
                                        onClick={() => setReview({ ...review!, rating: 2 })}>
                                        <FontAwesomeIcon icon={fullStars >= 2 ? fasStar : farStar}
                                            className="productForm__star" />
                                    </li>
                                    <li onMouseEnter={() => setFullStars(3)}
                                        onMouseLeave={() => setFullStars(review!.rating)}
                                        onClick={() => setReview({ ...review!, rating: 3 })}>
                                        <FontAwesomeIcon icon={fullStars >= 3 ? fasStar : farStar}
                                            className="productForm__star" />
                                    </li>
                                    <li onMouseEnter={() => setFullStars(4)}
                                        onMouseLeave={() => setFullStars(review!.rating)}
                                        onClick={() => setReview({ ...review!, rating: 4 })}>
                                        <FontAwesomeIcon icon={fullStars >= 4 ? fasStar : farStar}
                                            className="productForm__star" />
                                    </li>
                                    <li onMouseEnter={() => setFullStars(5)}
                                        onMouseLeave={() => setFullStars(review!.rating)}
                                        onClick={() => setReview({ ...review!, rating: 5 })}>
                                        <FontAwesomeIcon icon={fullStars === 5 ? fasStar : farStar} className="productForm__star" />
                                    </li>
                                </div>
                            </div>
                            <div className="cell medium-6 small-12">
                                <label className="productForm__label">Your name</label>
                                <input type="text" name="name" value={review.name}
                                    onChange={changeReview} required />
                            </div>
                            <div className="cell medium-6 small-12">
                                <label className="productForm__label">Subject</label>
                                <input type="text" name="title" value={review.title}
                                    onChange={changeReview} required />
                            </div>
                            <div className="cell small-12">
                                <label className="productForm__label">Body</label>
                                <textarea name="text" value={review.text} onChange={changeReview}
                                    required></textarea>
                            </div>
                            <div className="cell small-12">
                                <button className="primaryButton">Submit review</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}