package pl.damiankaplon;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.jboss.resteasy.reactive.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

@Path("/api/v1/candy/{candyId}/image")
@RequiredArgsConstructor
public class ImageResource {
    private final ImageService imageService;

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Multi<File> getCandyImages(@PathParam("candyId") String candyId) {
        return imageService.getMultiImages(new ObjectId(candyId));
    }

    @GET
    @Path("{imageNo}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Uni<File> getCandyImage(@PathParam("candyId") String candyId, @PathParam("imageNo") String id) {
        return imageService.getUniImage(new ObjectId(candyId), new ObjectId(id));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImages(@PathParam("candyId") String candyId, @MultipartForm ImageForm upload) throws IOException {
        imageService.upload(new ObjectId(candyId), upload);
        return Response.ok().build();
    }

}