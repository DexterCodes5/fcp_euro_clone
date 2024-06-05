import { AnimatePresence, motion } from "framer-motion"
import { useLocalStorage } from "../../hooks/useLocalStorage"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faCircleCheck, faX } from "@fortawesome/free-solid-svg-icons"
import { useEffect } from "react"

export const Notification = () => {
    const [notification, setNotification] = useLocalStorage<string>("notification", "")

    useEffect(() => {
        if (notification) {
            setTimeout(() => setNotification(""), 5000)
        }
    }, [notification])

    return (
        <AnimatePresence>
            {notification &&
                <motion.div className="notification"
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
                                <FontAwesomeIcon icon={faCircleCheck} className="notification__i" />
                                {notification}
                            </div>
                            <div className="notification__close">
                                <FontAwesomeIcon icon={faX} className="notification__closeI"
                                    onClick={() => setNotification("")} />
                            </div>
                        </div>
                    </div>
                </motion.div>
            }
        </AnimatePresence>
    )
}