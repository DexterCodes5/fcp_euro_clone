import { useEffect, useState } from "react"

export const useLocalStorage = <T,>(key: string, initialValue: T | (() => T)) => {
  const [value, setValue] = useState<T>(() => {
    const jsonValue = localStorage.getItem(key)
    if (jsonValue !== null) {
      if (typeof initialValue === "string") {
        return jsonValue
      }
      return JSON.parse(jsonValue)
    }

    if (typeof initialValue === "function") {
      return (initialValue as () => T)()
    }
    else {
      return initialValue
    }
  })

  useEffect(() => {
    if (typeof value === "string") {
      localStorage.setItem(key, value)
    } else {
      localStorage.setItem(key, JSON.stringify(value))
    }
  }, [key, value])

  return [value, setValue] as [typeof value, typeof setValue]
}