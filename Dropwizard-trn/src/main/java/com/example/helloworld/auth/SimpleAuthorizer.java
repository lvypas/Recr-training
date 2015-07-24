package com.example.helloworld.auth;

import com.example.helloworld.core.User;
import io.dropwizard.auth.Authorizer;

public class SimpleAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role) {
        if (role.equals("ADMIN") && !user.getName().startsWith("chief")) {
            return false;
        }
        return true;
    }
}
