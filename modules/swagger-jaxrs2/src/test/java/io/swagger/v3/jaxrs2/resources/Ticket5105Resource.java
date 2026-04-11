package io.swagger.v3.jaxrs2.resources;

import io.swagger.v3.oas.annotations.Operation;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/users")
public class Ticket5105Resource {

    @GET
    @Operation(summary = "List users")
    public UserResponse getUsers(
            @QueryParam("filter") String filter,
            @QueryParam("page") Integer page) {
        return new UserResponse();
    }

    @POST
    @Operation(summary = "Create user")
    public UserResponse createUser(Request request) {
        return new UserResponse();
    }

    public static class UserResponse {

    }

    public static class Request {

    }
}
