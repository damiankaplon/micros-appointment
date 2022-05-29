package pl.damiankaplon

import org.eclipse.microprofile.jwt.JsonWebToken
import pl.damiankaplon.service.AccountDTO
import pl.damiankaplon.service.Credentials
import pl.damiankaplon.service.SecurityService
import javax.annotation.security.RolesAllowed
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.RequestScoped
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/security")
@ApplicationScoped

class Endpoints(private val secService: SecurityService) {

    @POST
    @Path("token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getBearerToken(credentials: Credentials) = secService.getBearerToken(credentials)

    @POST
    @Path("account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun register(dto: AccountDTO) = secService.registerAccount(dto)

}