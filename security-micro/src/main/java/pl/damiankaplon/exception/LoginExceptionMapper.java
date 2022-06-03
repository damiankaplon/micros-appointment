package pl.damiankaplon.exception;

import io.quarkus.logging.Log;
import lombok.RequiredArgsConstructor;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;

@Provider
@RequiredArgsConstructor
public class LoginExceptionMapper implements ExceptionMapper<LoginException> {

    @Override
    public Response toResponse(LoginException exception) {
        Log.debug(exception.getMessage());
        Arrays.stream(exception.getStackTrace())
                .iterator()
                .forEachRemaining(ex -> Log.debug(ex.toString()));
        Log.info("LoginException thrown with message: " + exception.getMessage());
        return Response.status(401)
                .entity(exception.getMessage())
                .build();
    }
}