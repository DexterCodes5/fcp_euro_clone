import { useDocumentTitle } from "../../hooks/useDocumentTitle"

export const ErrorPage = () => {
    useDocumentTitle("Oops! Something Went Wrong")
    return (
        <h1>Error</h1>
    )
}