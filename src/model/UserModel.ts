export class UserModel {
    id: number
    email: string
    username: string
    role: string

    constructor(id: number, email: string, username: string, role: string) {
        this.id = id
        this.email = email
        this.username = username
        this.role = role
    }
}