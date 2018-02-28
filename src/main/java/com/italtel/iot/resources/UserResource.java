package com.italtel.iot.resources;

import com.codahale.metrics.annotation.Timed;
import com.italtel.iot.api.User;
import com.italtel.iot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by satriani on 23/10/2017.
 */
@Path("/user")
@RolesAllowed({"ADMIN"})
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(final UserService userService) {
        this.userService = userService;
    }

    @GET
    @Timed
    @Path("{username}")
    public Response getUser(@PathParam("username") final String username) {
        log.info("Get user: {}", username);
        return Response.ok(userService.getUser(username)).build();
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@NotNull @Valid final User user) {
        log.info("Create user: {}", user.getUsername());
        return Response.ok(userService.createUser(user)).build();
    }

    @PUT
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUser(@NotNull @Valid final User user) {
        log.info("Edit user: {}", user.getUsername());
        return Response.ok(userService.editUser(user)).build();
    }

    @DELETE
    @Timed
    @Path("{username}")
    public Response deleteUser(@PathParam("username") final String username) {
        log.info("Delete user: {}", username);
        userService.deleteUser(username);
        return Response.ok().build();
    }

}
