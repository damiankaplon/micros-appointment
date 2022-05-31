package pl.damiankaplon.service;


import javax.security.auth.login.LoginException;
import java.util.UUID;

public interface SecurityService {

    record Credentials(String login, String password){}
    record BearerToken(String token){}
    record AccountDTO(UUID uuid, String email, String name, String surname){}

    BearerToken getBearerToken(Credentials credentials) throws LoginException;

}
