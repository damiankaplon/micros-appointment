package pl.damiankaplon

import pl.damiankaplon.dto.CredentialsDTO
import pl.damiankaplon.service.SecurityService
import javax.ws.rs.POST
import javax.ws.rs.Path

@Path("/api/security")
class Endpoints(private val secService: SecurityService) {

    @POST
    @Path("token")
    fun getBearerToken(credentials: CredentialsDTO) = secService.getBearerToken(credentials)
}