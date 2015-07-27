package com.example.helloworld.auth;

import com.example.helloworld.core.Candidate;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, Candidate> {
    @Override
    public Optional<Candidate> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if ("secret".equals(credentials.getPassword())) {
            return Optional.of(new Candidate(credentials.getUsername()));
        }
        return Optional.absent();
    }
}
