package pl.damiankaplon.service;

import io.smallrye.jwt.build.Jwt;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.damiankaplon.entity.Account;
import pl.damiankaplon.repository.AccountRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.security.auth.login.LoginException;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class JwtSecurityService implements SecurityService{

    private final AccountRepository accountRepository;
    @ConfigProperty(name = "mp.jwt.verify.issuer") String issuer;

    @Override
    public BearerToken getBearerToken(Credentials credentials) throws LoginException {
        Account account = accountRepository.findByEmail(credentials.login())
                .orElseThrow(() -> new LoginException("Invalid Credentials"));
        if (!account.getPassword().equals(credentials.password()))
            throw new LoginException("Invalid Credentials");

        return new BearerToken(
                Jwt.issuer(issuer)
                        .subject(account.getEmail())
                        .expiresIn(360).sign()
        );
    }

    @Override
    public ObjectId register(Registration dto) throws LoginException {
        Optional<Account> optAcc = accountRepository.findByEmail(dto.email());
        if (optAcc.isPresent())
            throw new LoginException("Email is already occupied");

        Account account = Account.from(dto);
        accountRepository.persist(account);

        return account.getId();
    }

    @Override
    public Optional<AccountDTO> getAccountById(String id) {
        Optional<Account> account = accountRepository.findByIdOptional(new ObjectId(id));
        return account.map(value -> new AccountDTO(
                value.getEmail(),
                value.getName(),
                value.getSurname()
        ));
    }
}
