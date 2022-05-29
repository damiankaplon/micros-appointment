package pl.damiankaplon.service

import pl.damiankaplon.dto.CredentialsDTO

interface SecurityService {
    fun getBearerToken(credentials: CredentialsDTO): String
}