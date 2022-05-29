package pl.damiankaplon

import io.quarkus.arc.Priority
import pl.damiankaplon.entity.Account
import pl.damiankaplon.entity.AccountRepository
import java.util.*
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Alternative

@Priority(1)
@Alternative
@ApplicationScoped
class AccountRepositoryStub: AccountRepository() {
    @PostConstruct
    fun fillTestDb() {
        val some = Account()
        some.email = "email@email.com"
        some.uuid = UUID.randomUUID()
        some.name = "Sammy"
        some.surname = "Jersey"
        some.password = "password"
        some.accesses = "all"
        super.save(some)
    }
}