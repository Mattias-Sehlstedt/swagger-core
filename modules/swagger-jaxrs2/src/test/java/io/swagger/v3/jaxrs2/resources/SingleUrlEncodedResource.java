package io.swagger.v3.jaxrs2.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/things")
@Produces("application/json")
public class SingleUrlEncodedResource {

    @POST
    @Path("/search-id")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response searchForThing(@FormParam("id") String id) {
        return Response.status(200).entity("Searching for something").build();
    }
}
