package com.italtel.iot.resources;

import com.codahale.metrics.annotation.Timed;
import com.italtel.iot.api.TimeAlarm;
import com.italtel.iot.api.User;
import com.italtel.iot.services.TimeAlarmService;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by satriani on 23/10/2017.
 */
@Path("/alarm")
@PermitAll
public class TimeAlarmResource {

    private static final Logger log = LoggerFactory.getLogger(TimeAlarmResource.class);

    private final TimeAlarmService timeAlarmService;

    public TimeAlarmResource(final TimeAlarmService timeAlarmService) {
        this.timeAlarmService = timeAlarmService;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJsonTimeAlarm(@Auth User user) {
        return Response.ok(getTimeAlarm(user)).build();
    }

    @GET
    @Timed
    @Path("/text")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getTextTimerAlarm(@Auth User user) {
        return Response.ok(getTimeAlarm(user).toString()).build();
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTimeAlarm(@NotNull @Valid final TimeAlarm timeAlarm, @Auth User user) {
        log.info("Create time alarm for '{}': {}", user.getUsername(), timeAlarm);
        return Response.ok(timeAlarmService.createTimeAlarm(timeAlarm, user.getUsername())).build();
    }

    @PUT
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editTimeAlarm(@NotNull @Valid final TimeAlarm timeAlarm, @Auth User user) {
        log.info("Edit time alarm for '{}': {}", user.getUsername(), timeAlarm);
        return Response.ok(timeAlarmService.editTimeAlarm(timeAlarm, user.getUsername())).build();
    }

    @DELETE
    @Timed
      public Response deleteTimeAlarm(@Auth User user) {
        log.info("Delete time alarm for '{}'", user.getUsername());
        timeAlarmService.deleteTimeAlarm(user.getUsername());
        return Response.ok().build();
    }

    private TimeAlarm getTimeAlarm(User user) {
        String username = user.getUsername();
        log.info("Get time alarm for '{}'", username);
        TimeAlarm timeAlarm = timeAlarmService.getTimeAlarm(username);
        log.info("Got time alarm: {}", timeAlarm);
        return timeAlarm;
    }

}
