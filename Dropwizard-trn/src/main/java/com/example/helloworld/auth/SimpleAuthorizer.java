package com.example.helloworld.auth;

import com.example.helloworld.core.Candidate;
import io.dropwizard.auth.Authorizer;

public class SimpleAuthorizer implements Authorizer<Candidate> {

    @Override
    public boolean authorize(Candidate candidate, String role) {
        if (role.equals("ADMIN") && !candidate.getName().startsWith("chief")) {
            return false;
        }
        return true;
    }
}
