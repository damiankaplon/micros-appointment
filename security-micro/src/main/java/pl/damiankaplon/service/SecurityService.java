package pl.damiankaplon.service;


import io.smallrye.jwt.auth.principal.ParseException;
import org.bson.types.ObjectId;

import javax.security.auth.login.LoginException;
import java.util.Optional;

public interface SecurityService {

    record Credentials(String login, String password){}
    record BearerToken(String token){}
    record BearerTokenPair(BearerToken token, BearerToken refreshToken){}
    record Registration(String email, String password, String name, String surname){}
    record AccountDTO(String email, String name, String surname){}

    BearerTokenPair login(Credentials credentials) throws LoginException;
    ObjectId register(Registration dto) throws LoginException;
    BearerToken refreshToken(BearerToken token, BearerToken refreshToken) throws ParseException, LoginException;
    Optional<AccountDTO> getAccountById(String id);

}
