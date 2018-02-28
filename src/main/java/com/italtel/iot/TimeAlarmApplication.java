package com.italtel.iot;

import com.italtel.iot.api.User;
import com.italtel.iot.auth.UserAuthenticator;
import com.italtel.iot.auth.UserAuthorizer;
import com.italtel.iot.resources.TimeAlarmResource;
import com.italtel.iot.resources.UserResource;
import com.italtel.iot.services.TimeAlarmService;
import com.italtel.iot.services.UserService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;

public class TimeAlarmApplication extends Application<TimeAlarmConfiguration> {

    private static final String SQL = "sql";

    public static void main(final String[] args) throws Exception {
        new TimeAlarmApplication().run(args);
    }

    @Override
    public String getName() {
        return "TimeAlarm";
    }

    @Override
    public void initialize(final Bootstrap<TimeAlarmConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)));
        bootstrap.addBundle(new DBIExceptionsBundle());
    }

    @Override
    public void run(final TimeAlarmConfiguration configuration,
                    final Environment environment) {
        // Datasource configurations
        final DataSource dataSource =
                configuration.getDataSourceFactory().build(environment.metrics(), SQL);
        DBI dbi = new DBI(dataSource);

        // Services
        UserService userService = dbi.onDemand(UserService.class);
        TimeAlarmService timeAlarmService = dbi.onDemand(TimeAlarmService.class);

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new UserAuthenticator(userService))
                        .setAuthorizer(new UserAuthorizer())
                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        // Register resources
        environment.jersey().register(new UserResource(userService));
        environment.jersey().register(new TimeAlarmResource(timeAlarmService));
    }



}
