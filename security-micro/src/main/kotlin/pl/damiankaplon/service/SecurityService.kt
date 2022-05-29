package pl.damiankaplon.service

data class Credentials(val login: String, val password: String)
interface SecurityService {
    fun getBearerToken(credentials: Credentials): String
}