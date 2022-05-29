package pl.damiankaplon.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["email"])
    ])
internal class Account: PanacheEntity() {

    lateinit var uuid: UUID
    lateinit var email: String
    lateinit var name: String
    lateinit var surname: String
    lateinit var password: String
    lateinit var accesses: String
    companion object: PanacheCompanion<Account> {
        fun findByEmail(email: String) = find("email", email).firstResult()
        fun findByUUID(uuid: UUID) = find("uuid", uuid).firstResult()
        fun save(account: Account) = persist(account)
    }
}