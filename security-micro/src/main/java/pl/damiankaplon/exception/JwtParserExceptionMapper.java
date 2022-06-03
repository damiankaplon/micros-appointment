package pl.damiankaplon.exception;

import io.smallrye.jwt.auth.principal.ParseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JwtParserExceptionMapper implements ExceptionMapper<ParseException> {

    @Override
    public Response toResponse(ParseException exception) {
        return Response.status(401)
                .entity("Error while parsing JWT. It might be wrong formatted. Never modify JWT which we sent you")
                .build();
    }
}