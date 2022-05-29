package pl.damiankaplon.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.config.inject.ConfigProperty
import pl.damiankaplon.entity.Account
import java.time.LocalDateTime
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.security.auth.login.LoginException
import javax.transaction.Transactional

@ApplicationScoped
class StatelessService @Inject constructor(private val mapper: ObjectMapper): SecurityService {

    @Transactional
    override fun getBearerToken(credentials: Credentials): String {
        val account: Account = Account.findByEmail(credentials.login) ?: throw LoginException("Email is not existing")
        if (account.password != credentials.password)
            throw LoginException("Invalid Credentials")

       return Jwt
           .subject(account.email)
           .groups(account.accesses)
           .expiresIn(3600)
           .sign()
    }

}