package com.italtel.iot.services;

import com.italtel.iot.api.User;
import com.italtel.iot.db.dao.UserDao;
import com.italtel.iot.utility.SQLExceptionUtility;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

/**
 * Created by satriani on 23/10/2017.
 */
public abstract class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private static final String ERROR_NOT_FOUND = "User '%s' not found";
    private static final String ERROR_SINGLE_READING = "An error occurred while reading user '%s'";
    private static final String ERROR_CREATING = "An error occurred while creating user '%s'";
    private static final String ERROR_UPDATING = "An error occurred while updating user '%s'";
    private static final String ERROR_DELETING = "An error occurred while deleting user '%s'";

    @CreateSqlObject
    abstract UserDao userDao();

    public User getUser(final String username) {
        User user;
        try {
            user = userDao().getUser(username);
        } catch (Throwable t) {
            log.error(String.format(ERROR_SINGLE_READING, username) + ": {}", t.getMessage());
            throw new InternalServerErrorException(String.format(ERROR_SINGLE_READING, username));
        }

        if (user == null) {
            throw new NotFoundException(String.format(ERROR_NOT_FOUND, username));
        }
        return user;
    }

    @Transaction
    public User createUser(final User user) {
        try {
            userDao().createUser(user);
            return user;
        } catch (Throwable t) {
            log.error(String.format(ERROR_CREATING, user.getUsername()) + ": {}", t.getMessage());
            String violationMsg = SQLExceptionUtility.getConstraintViolation(t,
                    SQLExceptionUtility.OperationType.CREATE);
            throw new InternalServerErrorException(String.format(ERROR_CREATING, user.getUsername()) +
                    (violationMsg == null ? "" : ": " + violationMsg));
        }
    }

    @Transaction
    public User editUser(final User user) {
        try {
            userDao().editUser(user);
            return user;
        } catch (Throwable t) {
            log.error(String.format(ERROR_UPDATING, user.getUsername()) + ": {}", t.getMessage());
            String violationMsg = SQLExceptionUtility.getConstraintViolation(t,
                    SQLExceptionUtility.OperationType.UPDATE);
            throw new InternalServerErrorException(String.format(ERROR_UPDATING, user.getUsername()) +
                    (violationMsg == null ? "" : ": " + violationMsg));
        }
    }

    @Transaction
    public void deleteUser(final String username) {
        try {
            userDao().deleteUser(username);
        } catch (Throwable t) {
            log.error(String.format(ERROR_DELETING, username) + ": {}", t.getMessage());
            String violationMsg = SQLExceptionUtility.getConstraintViolation(t,
                    SQLExceptionUtility.OperationType.DELETE);
            throw new InternalServerErrorException(String.format(ERROR_DELETING, username) +
                    (violationMsg == null ? "" : ": " + violationMsg));
        }
    }

}
