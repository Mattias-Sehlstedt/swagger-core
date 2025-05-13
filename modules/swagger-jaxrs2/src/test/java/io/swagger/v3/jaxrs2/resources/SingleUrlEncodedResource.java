package io.swagger.v3.jaxrs2.resources;

import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/things")
@Produces("application/json")
public class SingleUrlEncodedResource {

    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response searchForThings(
            @Parameter(name = "id", description = "id param") @FormParam("id") List<String> ids) {
        return Response.status(200).entity("Sriracha!").build();
    }

    @POST
    @Path("/search-id")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response searchForThing(@BeanParam String id) {
        return Response.status(200).entity("Searching for something").build();
    }
}
