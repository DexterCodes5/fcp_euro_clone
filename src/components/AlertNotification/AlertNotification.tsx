import { AnimatePresence, motion } from "framer-motion"
import { useLocalStorage } from "../../hooks/useLocalStorage"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faExclamationTriangle, faX } from "@fortawesome/free-solid-svg-icons"
import { useEffect } from "react"

export const AlertNotification = () => {
    const [alertNotification, setAlertNotification] = useLocalStorage("alert-notification", "")

    useEffect(() => {
        if (alertNotification) {
            setTimeout(() => setAlertNotification(""), 5000)
        }
    }, [])

    return (
        <AnimatePresence>
            {alertNotification &&
                <motion.div className="notification notification--alert"
                    initial={{
                        height: 0,
                        padding: 0
                    }}
                    animate={{
                        height: "auto",
                        padding: ".5rem"
                    }}
                    exit={{
                        height: 0,
                        padding: 0
                    }}
                >
                    <div className="grid-container">
                        <div className="notification__container">
                            <div className="notification__message">
                                <FontAwesomeIcon icon={faExclamationTriangle} className="notification__i" />
                                {alertNotification}
                            </div>
                            <div className="notification__close">
                                <FontAwesomeIcon icon={faX} className="notification__closeI"
                                    onClick={() => setAlertNotification("")} />
                            </div>
                        </div>
                    </div>
                </motion.div>
            }
        </AnimatePresence>
    )
}