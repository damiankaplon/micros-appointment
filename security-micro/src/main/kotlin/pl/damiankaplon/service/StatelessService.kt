package pl.damiankaplon.service

import pl.damiankaplon.dto.CredentialsDTO
import pl.damiankaplon.entity.Account
import java.util.*
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.security.auth.login.LoginException
import javax.transaction.Transactional

@ApplicationScoped
internal class StatelessService: SecurityService {

    @Transactional
    override fun getBearerToken(credentials: CredentialsDTO): String {
        val account: Account = Account.findByEmail(credentials.login) ?: throw LoginException("Email is not existing")
        if (account.password != credentials.password)
            throw LoginException("Invalid Credentials")
        return "Bearer"
    }

}