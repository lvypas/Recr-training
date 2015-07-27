package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.jdbi.CandidateDAO;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/candidate/{personId}")
@Produces(MediaType.APPLICATION_JSON)
public class CandidateResource {
    private final CandidateDAO candidateDAO;

    public CandidateResource(CandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    @GET
    @Timed
    public String findNameById(@PathParam("personId") Integer id) {
        final String userName = candidateDAO.findNameById(id);
        if (userName.isEmpty()) {
            throw new NotFoundException("No such candidate.");
        }
        return userName;
    }
}
