package com.italtel.iot.db.dao;

import com.italtel.iot.api.TimeAlarm;
import com.italtel.iot.db.mapper.TimeAlarmMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by satriani on 23/10/2017.
 */
@RegisterMapper(TimeAlarmMapper.class)
public interface TimeAlarmDao {

    @SqlQuery("select hours, minutes from alarms a " +
            "inner join users u on u.id = a.user_id and u.username = :username")
    TimeAlarm getTimeAlarm(@Bind("username") final String username);

    @SqlUpdate("insert into alarms (hours, minutes, user_id) values(:hours, :minutes, " +
            "(select id from users where username = :username))")
    void createTimeAlarm(@BindBean final TimeAlarm timeAlarm, @Bind("username") final String username);

    @SqlUpdate("update a set a.hours = :hours, a.minutes = :minutes from alarms a " +
            "inner join users u on u.id = a.user_id and u.username = :username")
    void editTimeAlarm(@BindBean final TimeAlarm timeAlarm, @Bind("username") final String username);

    @SqlUpdate("delete from alarms a " +
            "inner join users u on u.id = a.user_id and u.username = :username")
    int deleteTimeAlarm(@Bind("username") final String username);
}
