package pl.damiankaplon.service;

import io.smallrye.jwt.build.Jwt;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.damiankaplon.entity.Account;
import pl.damiankaplon.repository.AccountRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.security.auth.login.LoginException;

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
}
