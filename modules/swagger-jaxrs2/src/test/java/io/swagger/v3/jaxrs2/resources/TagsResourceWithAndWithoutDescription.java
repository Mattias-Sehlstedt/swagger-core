package io.swagger.v3.jaxrs2.resources;

import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class TagsResourceWithAndWithoutDescription {

    @GET
    @Path("/")
    @Tag(name = "First Tag", description = "desc")
    @Tag(name = "Second Tag")
    @Tag(name = "Third Tag", kind = "kind")
    public Response getTags() {
        return Response.ok().entity("ok").build();
    }
}
