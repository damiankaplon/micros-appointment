package pl.damiankaplon;

import io.smallrye.jwt.auth.principal.ParseException;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestHeader;
import pl.damiankaplon.service.SecurityService;

import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static pl.damiankaplon.service.SecurityService.Credentials;
import static pl.damiankaplon.service.SecurityService.Registration;
import static pl.damiankaplon.service.SecurityService.LoginResponse;
import static pl.damiankaplon.service.SecurityService.BearerToken;

@Path("/api/v1/security/account")
@RequiredArgsConstructor
public class AccountResource {

    private final SecurityService securityService;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBearerTokenPair(Credentials credentials) throws LoginException {
        LoginResponse tokensPair = securityService.login(credentials);
        return Response.ok().entity(tokensPair).build();
    }

    @POST
    @Path("token/refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshBearerToken(@RestHeader("Authorization") String bearer,
                                          BearerToken refreshToken) throws LoginException, ParseException {
        String tokenString = bearer.replace("Bearer ", "");
        BearerToken token = new BearerToken(tokenString);
        return Response.ok()
                .entity(securityService.refreshToken(token, refreshToken))
                .build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(Registration dto) throws LoginException {
        securityService.register(dto);
        return Response.ok()
                .build();
    }
}