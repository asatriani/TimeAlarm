package com.italtel.iot.db.mapper;

import com.italtel.iot.api.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by satriani on 23/10/2017.
 */
public class UserMapper implements ResultSetMapper <User> {

    private static final String USERNAME = "username";
    private static final String PASSWD = "password";
    private static final String ROLE = "role";

    public User map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        return new User(resultSet.getString(USERNAME), resultSet.getString(PASSWD),
                User.Role.valueOf(resultSet.getString(ROLE)));
    }
}
