package pl.damiankaplon.service

import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.config.inject.ConfigProperty
import pl.damiankaplon.entity.Account
import pl.damiankaplon.entity.AccountRepository
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.security.auth.login.LoginException
import javax.transaction.Transactional
import javax.xml.bind.ValidationException

@ApplicationScoped
class StatelessService(
    private val accRepo: AccountRepository,
    @ConfigProperty(name = "mp.jwt.verify.issuer") val issuer: String
    ): SecurityService {

    @Transactional
    override fun getBearerToken(credentials: Credentials): BearerToken {
        val account: Account = accRepo.findByEmail(credentials.login) ?: throw LoginException("Email is not existing")
        if (account.password != credentials.password)
            throw LoginException("Invalid Credentials")

       return BearerToken(
               Jwt
                   .issuer(issuer)
                   .subject(account.email)
                   .groups(account.accesses)
                   .expiresIn(360)
                   .sign()
       )
    }

    override fun registerAccount(dto: AccountDTO): UUID {
        val toRegister = Account.from(dto)
        accRepo.save(toRegister)
        return toRegister.uuid
    }

}