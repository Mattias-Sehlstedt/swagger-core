package io.swagger.v3.jaxrs2.petstore.tags;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@OpenAPIDefinition(tags = {
        @Tag(name = "Definition First Tag"),
        @Tag(name = "Definition Second Tag full", description = "desc definition")
})
@Tag(name = "Second Tag", description = "desc class", parent = "Example Tag", kind = "nav")
@Tag(name = "Fourth Tag Full", description = "desc class", externalDocs = @ExternalDocumentation(description = "docs desc class"))
@Tag(name = "Fifth Tag Full", description = "desc class", externalDocs = @ExternalDocumentation(description = "docs desc class"))
@Tag(name = "Sixth Tag")
public class CompleteTag32Resource {

    @GET
    @Path("/completetags")
    @Operation(tags = {"Example Tag", "Second Tag"})
    @Tag(name = "Third Tag")
    @Tag(name = "Second Tag")
    @Tag(name = "Fourth Tag Full", description = "desc", externalDocs = @ExternalDocumentation(description = "docs desc"))
    public Response getTags() {
        return Response.ok().entity("ok").build();
    }

    @GET
    @Path("/completetags")
    @Operation(tags = {"Second Tag"})
    @Tag(name = "Third Tag", parent = "Definition First Tag")
    @Tag(name = "Second Tag", description = "desc class", parent = "Example Tag", kind = "nav")
    @Tag(name = "Fourth Tag Full", description = "desc", externalDocs = @ExternalDocumentation(description = "docs desc"))
    public Response getTagsWithParent() {
        return Response.ok().entity("ok").build();
    }
}
