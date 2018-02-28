package com.italtel.iot.db.dao;

import com.italtel.iot.api.User;
import com.italtel.iot.db.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by satriani on 23/10/2017.
 */
@RegisterMapper(UserMapper.class)
public interface UserDao {

    @SqlQuery("select username, password, role from users where username = :username")
    User getUser(@Bind("username") final String username);

    @SqlUpdate("insert into users (username, password, role) values(:username, :password, :role)")
    void createUser(@BindBean final User user);

    @SqlUpdate("update users set password = :password, role = :role where username = :username")
    void editUser(@BindBean final User user);

    @SqlUpdate("delete from users where username = :username")
    int deleteUser(@Bind("username") final String username);
}
