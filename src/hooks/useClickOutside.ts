import { useEffect } from "react"

export const useClickOutside = (ref: React.MutableRefObject<any>, onClickOutside: Function) => {
    useEffect(() => {
        const handleClickOutside = (e: any) => {
            if (ref.current && !ref.current.contains(e.target)) {
                onClickOutside()
            }
        }

        document.addEventListener("click", handleClickOutside)
        return () => document.removeEventListener("click", handleClickOutside)
    }, [])
}