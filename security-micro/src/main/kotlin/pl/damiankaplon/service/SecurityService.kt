package pl.damiankaplon.service

import java.util.UUID

data class Credentials(val login: String, val password: String)
data class BearerToken(val token: String)
data class AccountDTO(val uuid: UUID, val email: String, val name: String?, val surname: String?, val password: String)
interface SecurityService {
    fun getBearerToken(credentials: Credentials): BearerToken
    fun registerAccount(dto: AccountDTO): UUID
}