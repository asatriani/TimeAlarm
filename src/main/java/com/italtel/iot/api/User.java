package com.italtel.iot.api;

import javax.validation.constraints.NotNull;
import java.security.Principal;

/**
 * Created by satriani on 26/02/2018.
 */
public class User implements Principal {

    public enum Role {
        ADMIN,
        USER
    }

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Role role;


    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String getName() {
        return username;
    }

}
