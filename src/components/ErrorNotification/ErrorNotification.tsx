import { AnimatePresence, motion } from "framer-motion"
import { useLocalStorage } from "../../hooks/useLocalStorage"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faCircleExclamation, faX } from "@fortawesome/free-solid-svg-icons"

export const ErrorNotificaition = () => {
    const [errorNotification, setErrorNotification] = useLocalStorage<string>("error-notification", "error-notification")

    return (
        <AnimatePresence>
            {errorNotification &&
                <motion.div className="notification notification--error"
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
                                <FontAwesomeIcon icon={faCircleExclamation} className="notification__i" />
                                {errorNotification}
                            </div>
                            <div className="notification__close">
                                <FontAwesomeIcon icon={faX} className="notification__closeI"
                                    onClick={() => setErrorNotification("")} />
                            </div>
                        </div>
                    </div>
                </motion.div>
            }
        </AnimatePresence>
    )
}