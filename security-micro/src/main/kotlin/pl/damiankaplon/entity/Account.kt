package pl.damiankaplon.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import pl.damiankaplon.service.AccountDTO
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["email"])
    ])
class Account{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    lateinit var uuid: UUID
    lateinit var email: String
    lateinit var name: String
    lateinit var surname: String
    lateinit var password: String
    lateinit var accesses: String

    companion object: EntityMapper<Account, AccountDTO> {
        override fun to(t: Account): AccountDTO {
            return AccountDTO(
                uuid = t.uuid,
                email = t.email,
                name = t.name,
                surname = t.surname,
                password = t.password
            )
        }

        override fun from(e: AccountDTO): Account {
            val account = Account()
            account.uuid = UUID.randomUUID()
            account.email = e.email
            account.password = e.password
            account.name = e.name ?: "not specified"
            account.surname = e.surname ?: "not specified"
            return account
        }
    }

}

@ApplicationScoped
class AccountRepository: PanacheRepository<Account> {
    fun findByEmail(email: String) = find("email", email).firstResult()
    fun findByUUID(uuid: UUID) = find("uuid", uuid).firstResult()
    fun save(account: Account) = persist(account)
}
