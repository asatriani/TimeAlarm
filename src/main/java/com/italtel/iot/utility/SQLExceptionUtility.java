package com.italtel.iot.utility;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Created by satriani on 28/11/2017.
 */
public class SQLExceptionUtility {

    public enum OperationType {
        CREATE,
        UPDATE,
        DELETE
    }

    public static String getConstraintViolation(Throwable t, OperationType opType) {
        SQLIntegrityConstraintViolationException constraintViolationEx = null;
        if (SQLIntegrityConstraintViolationException.class.isAssignableFrom(t.getClass())) {
            constraintViolationEx = (SQLIntegrityConstraintViolationException) t;
        } else if (UnableToExecuteStatementException.class.isAssignableFrom(t.getClass()) &&
                SQLIntegrityConstraintViolationException.class.isAssignableFrom(t.getCause().getClass())) {
            constraintViolationEx = (SQLIntegrityConstraintViolationException) t.getCause();
        }

        if (constraintViolationEx == null) {
            return null;
        }

        if (opType == OperationType.DELETE) {
            return "Cannot delete this object because another object referred it";
        } else {
            return "Cannot "  + (opType == OperationType.CREATE ? "create" : "update") +
                    " this object because already exists another object with key " +
                    constraintViolationEx.getMessage().split("\'")[1];
        }
    }
}
