package pl.damiankaplon.service;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import pl.damiankaplon.entity.Account;
import pl.damiankaplon.repository.AccountRepository;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.security.auth.login.LoginException;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class JwtSecurityService implements SecurityService{

    private final AccountRepository accountRepository;
    private final JWTParser jwtParser;
    @ConfigProperty(name = "mp.jwt.verify.issuer") String issuer;

    @Override
    public LoginResponse login(Credentials credentials) throws LoginException {
        Account account = accountRepository.findByEmail(credentials.login())
                .orElseThrow(() -> new LoginException("Invalid Credentials"));
        if (!account.getPassword().equals(credentials.password()))
            throw new LoginException("Invalid Credentials");

        BearerToken token = new BearerToken(
                Jwt.issuer(issuer)
                        .subject(account.getId().toString())
                        .groups(account.getGroups())
                        .expiresIn(360 * 5)
                        .sign());

        BearerToken refreshToken = new BearerToken(
                Jwt.issuer(issuer)
                        .subject(account.getId().toString())
                        .groups(account.getGroups())
                        .expiresIn(360 * 60 * 12)
                        .sign());

        return new LoginResponse(token, refreshToken, account.getId().toString());
    }

    @Override
    @PermitAll
    public BearerToken refreshToken(BearerToken token, BearerToken refreshToken) throws ParseException, LoginException {
        JsonWebToken toBeRefreshed = jwtParser.parse(token.token());
        JsonWebToken refresher = jwtParser.parse(refreshToken.token());

        if (!toBeRefreshed.getSubject().equals(refresher.getSubject()))
            throw new LoginException("Tokens dont match");

        return new BearerToken(
                Jwt.issuer(issuer)
                        .subject(toBeRefreshed.getSubject())
                        .expiresIn(360 * 5)
                        .sign()
        );
    }

    @Override
    public ObjectId register(Registration dto) throws LoginException {
        Optional<Account> optAcc = accountRepository.findByEmail(dto.email());
        if (optAcc.isPresent())
            throw new LoginException("Email is already occupied");

        Account account = Account.from(dto);
        account.setGroups(Set.of("USER"));
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
