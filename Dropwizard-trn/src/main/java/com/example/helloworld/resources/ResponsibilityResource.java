package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.jdbi.ResponsibilityDAO;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/responsibility/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class ResponsibilityResource {
    private final ResponsibilityDAO responsibilityDAO;

    public ResponsibilityResource(ResponsibilityDAO responsibilityDAO) {
        this.responsibilityDAO = responsibilityDAO;
    }

    @GET
    @Timed
    public String findNameById(@Auth @PathParam("id") Integer id) {
        final String userName = responsibilityDAO.findNameById(id);
        if (userName.isEmpty()) {
            throw new NotFoundException("No such responsibility.");
        }
        return userName;
    }
}