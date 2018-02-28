package com.italtel.iot.auth;

import com.italtel.iot.api.User;
import com.italtel.iot.services.UserService;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

public class UserAuthenticator implements Authenticator <BasicCredentials, User> {

    private final UserService userService;

    public UserAuthenticator(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional <User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        User user = userService.getUser(credentials.getUsername());
        if (user != null && user.getPassword().equals(credentials.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}