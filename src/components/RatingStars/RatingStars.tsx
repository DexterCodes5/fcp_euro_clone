import { faStar as fasStar } from "@fortawesome/free-solid-svg-icons"
import { faStar as farStar } from "@fortawesome/free-regular-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import "./RatingStars.css"

export const RatingStars: React.FC<{ rating: number }> = ({rating}) => {
    const widthPercentage = (rating / 5) * 100
    // console.log(widthPercentage)
    return (
        <div className="ratingStars">
            <div className="ratingStars__top" style={{ width: `${widthPercentage}%` }}>
                <FontAwesomeIcon icon={fasStar} className="ratingStars__star" />
                <FontAwesomeIcon icon={fasStar} className="ratingStars__star" />
                <FontAwesomeIcon icon={fasStar} className="ratingStars__star" />
                <FontAwesomeIcon icon={fasStar} className="ratingStars__star" />
                <FontAwesomeIcon icon={fasStar} className="ratingStars__star" />
            </div>
            <div className="ratingStars__bottom">
                <FontAwesomeIcon icon={farStar} className="ratingStars__star" />
                <FontAwesomeIcon icon={farStar} className="ratingStars__star" />
                <FontAwesomeIcon icon={farStar} className="ratingStars__star" />
                <FontAwesomeIcon icon={farStar} className="ratingStars__star" />
                <FontAwesomeIcon icon={farStar} className="ratingStars__star" />
            </div>
        </div>
    )
}