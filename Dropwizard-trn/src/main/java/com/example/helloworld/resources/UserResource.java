package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.core.User;
import com.example.helloworld.jdbi.UserDAO;
import io.dropwizard.auth.Auth;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user/{personId}")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO ;
    }

    @PermitAll
    @GET
    @Timed
    public String findNameById(@Auth User user,  @PathParam("personId") Integer id) {
        final String userName = userDAO.findNameById(id);
        if (userName.isEmpty()) {
            throw new NotFoundException("No such user.");
        }
        return userName;
    }
}
