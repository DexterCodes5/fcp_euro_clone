export const Errors: React.FC<{
    errors: string[]
}> = ({errors}) => (
    <div className="errors">
        <ul className="errors__list">
            {errors.map((err, i) =>
                <li key={i}>{err}</li>
            )}
        </ul>
    </div>
)