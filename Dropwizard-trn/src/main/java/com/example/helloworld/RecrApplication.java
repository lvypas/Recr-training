package com.example.helloworld;

import com.example.helloworld.auth.SimpleAuthenticator;
import com.example.helloworld.auth.SimpleAuthorizer;
import com.example.helloworld.core.User;
import com.example.helloworld.jdbi.UserDAO;
import com.example.helloworld.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class RecrApplication extends Application<RecrConfiguration> {
    public static void main(String[] args) throws Exception {
        new RecrApplication().run(args);
    }

    @Override
    public String getName() {
        return "Recr application";
    }

    @Override
    public void initialize(Bootstrap<RecrConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(RecrConfiguration configuration,
                    Environment environment) {

        /*environment.jersey().register(AuthFactory.binder(new BasicAuthFactory<String>((io.dropwizard.auth.Authenticator) new SimpleAuthenticator(),
                "secret",
                String.class)));*/
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new SimpleAuthenticator())
                .setAuthorizer(new SimpleAuthorizer())
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        final UserDAO dao = jdbi.onDemand(UserDAO.class);
        environment.jersey().register(new UserResource(dao));

    }
}