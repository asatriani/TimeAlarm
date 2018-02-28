package com.italtel.iot.db.mapper;

import com.italtel.iot.api.TimeAlarm;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by satriani on 23/10/2017.
 */
public class TimeAlarmMapper implements ResultSetMapper <TimeAlarm> {

    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";

    public TimeAlarm map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        return new TimeAlarm(resultSet.getInt(HOUR), resultSet.getInt(MINUTE));
    }
}
