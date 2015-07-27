package com.example.helloworld.jdbi;


import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface CandidateDAO {

    @SqlQuery("select \"FirstName\" from \"Candidate\" where id = :id")
    String findNameById(@Bind("id") int id);

}

