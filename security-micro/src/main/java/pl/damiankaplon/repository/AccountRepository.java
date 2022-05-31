package pl.damiankaplon.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import pl.damiankaplon.entity.Account;
import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class AccountRepository implements PanacheMongoRepository<Account> {

    public Optional<Account> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
