import { useEffect, useState } from "react"
import { RatingStars } from "../../../../components/RatingStars/RatingStars"
import axios from "axios"
import { ReviewModel } from "../../../../model/ReviewModel"
import { PartModel } from "../../../../model/PartModel"
import { Link, useParams } from "react-router-dom"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faPenToSquare } from "@fortawesome/free-solid-svg-icons"

export const Review: React.FC<{
    reviewTab: React.RefObject<HTMLDivElement>, reviews: ReviewModel[], averageRating: number
}> = ({ reviewTab, reviews, averageRating }) => {
    const params = useParams()

    const renderReview = (review: ReviewModel) => {
        const date = new Date(review.createdAt)
        const options: Intl.DateTimeFormatOptions = {
            weekday: 'short',
            month: 'short',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            hour12: true
        }
        const dateString = date.toLocaleDateString("en-US", options)
        const [weekday, monthAndDay, time] = dateString.split(",")
        return (
            <div className="review" key={review.id}>
                <div className="review__author">
                    <span>{review.name}</span>
                    <div className="review__createdAt">
                        on {weekday}, {monthAndDay} at {time}
                    </div>
                </div>
                <div className="review__rating">
                    <RatingStars rating={review.rating} />
                    <div className="review__title">{review.title}</div>
                </div>
                <div className="review__text">{review.text}</div>
            </div>
        )
    }

    return (
        <div className="tabs-panel" ref={reviewTab}>
            <h2>Product Reviews</h2>
            {reviews.length > 0 ?
                <>
                    <div className="extended__reviewOverview">
                        <h4>{reviews.length} Reviews</h4>
                        <div className="extended__reviewAverage">
                            <RatingStars rating={averageRating} />
                            {averageRating}
                        </div>
                        <div className="extended__reviewSubtitle"> Average rating </div>
                    </div>
                    <Link to={`/products/${params.product}/reviews/new`}>
                        <FontAwesomeIcon icon={faPenToSquare} />
                        &nbsp;Leave a review
                    </Link>
                    <div className="extended__reviews">
                        {reviews.map(renderReview)}
                    </div>
                </>
                :
                <div className="reviews__empty">
                    There are no reviews yet.&nbsp;
                    <Link to={`/products/${params.product}/reviews/new`}>Be the first to leave a review</Link>
                </div>
            }
        </div>
    )
}