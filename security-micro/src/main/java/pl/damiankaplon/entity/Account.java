package pl.damiankaplon.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Set;

import static pl.damiankaplon.service.SecurityService.Registration;

@MongoEntity(database = "database-security", collection = "accounts")
@Getter
@Setter

public class Account {
    private ObjectId id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Set<String> groups;
    public static Account from(Registration dto) {
        Account account = new Account();
        account.setEmail(dto.email());
        account.setPassword(dto.password());
        account.setSurname(dto.surname());
        account.setName(dto.name());
        return account;
    }
}
