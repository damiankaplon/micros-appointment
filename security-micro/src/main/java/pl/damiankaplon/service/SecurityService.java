package pl.damiankaplon.service;


import org.bson.types.ObjectId;

import javax.security.auth.login.LoginException;
import java.util.Optional;

public interface SecurityService {

    record Credentials(String login, String password){}
    record BearerToken(String token){}
    record Registration(String email, String password, String name, String surname){}
    record AccountDTO(String email, String name, String surname){}

    BearerToken getBearerToken(Credentials credentials) throws LoginException;
    ObjectId register(Registration dto) throws LoginException;
    Optional<AccountDTO> getAccountById(String id);

}
