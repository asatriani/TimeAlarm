package com.italtel.iot.services;

import com.italtel.iot.api.TimeAlarm;
import com.italtel.iot.db.dao.TimeAlarmDao;
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
public abstract class TimeAlarmService {

    private static final Logger log = LoggerFactory.getLogger(TimeAlarmService.class);

    private static final String ERROR_NOT_FOUND = "Time alarm for username '%s' not found";
    private static final String ERROR_SINGLE_READING = "An error occurred while reading time alarm " +
            "for username '%s'";
    private static final String ERROR_CREATING = "An error occurred while creating time alarm " +
            "for username '%s'";
    private static final String ERROR_UPDATING = "An error occurred while updating time alarm " +
            "for username '%s'";
    private static final String ERROR_DELETING = "An error occurred while deleting time alarm " +
            "for username '%s'";

    @CreateSqlObject
    abstract TimeAlarmDao timeAlarmDao();

    public TimeAlarm getTimeAlarm(final String username) {
        try {
            return timeAlarmDao().getTimeAlarm(username);
        } catch (Throwable t) {
            log.error(String.format(ERROR_SINGLE_READING, username) + ": {}", t.getMessage());
            throw new InternalServerErrorException(String.format(ERROR_SINGLE_READING, username));
        }
    }

    @Transaction
    public TimeAlarm createTimeAlarm(final TimeAlarm timeAlarm, final String username) {
        try {
            timeAlarmDao().createTimeAlarm(timeAlarm, username);
            return timeAlarm;
        } catch (Throwable t) {
            log.error(String.format(ERROR_CREATING, username) + ": {}", t.getMessage());
            String violationMsg = SQLExceptionUtility.getConstraintViolation(t,
                    SQLExceptionUtility.OperationType.CREATE);
            throw new InternalServerErrorException(String.format(ERROR_CREATING, username) +
                    (violationMsg == null ? "" : ": " + violationMsg));
        }
    }

    @Transaction
    public TimeAlarm editTimeAlarm(final TimeAlarm timeAlarm, final String username) {
        try {
            timeAlarmDao().editTimeAlarm(timeAlarm, username);
            return timeAlarm;
        } catch (Throwable t) {
            log.error(String.format(ERROR_UPDATING, username) + ": {}", t.getMessage());
            String violationMsg = SQLExceptionUtility.getConstraintViolation(t,
                    SQLExceptionUtility.OperationType.UPDATE);
            throw new InternalServerErrorException(String.format(ERROR_UPDATING, username) +
                    (violationMsg == null ? "" : ": " + violationMsg));
        }
    }

    @Transaction
    public void deleteTimeAlarm(final String username) {
        if (getTimeAlarm(username) == null) {
            throw new NotFoundException(String.format(ERROR_NOT_FOUND, username));
        }

        try {
            timeAlarmDao().deleteTimeAlarm(username);
        } catch (Throwable t) {
            log.error(String.format(ERROR_DELETING, username) + ": {}", t.getMessage());
            String violationMsg = SQLExceptionUtility.getConstraintViolation(t,
                    SQLExceptionUtility.OperationType.DELETE);
            throw new InternalServerErrorException(String.format(ERROR_DELETING, username) +
                    (violationMsg == null ? "" : ": " + violationMsg));
        }
    }

}
