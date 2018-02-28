package com.italtel.iot.auth;

import com.italtel.iot.api.User;
import io.dropwizard.auth.Authorizer;

/**
 * Created by satriani on 27/02/2018.
 */
public class UserAuthorizer implements Authorizer <User> {
    @Override
    public boolean authorize(User user, String role) {
        User.Role userRole = user.getRole();
        return userRole == User.Role.ADMIN || userRole.name().equals(role);
    }
}
