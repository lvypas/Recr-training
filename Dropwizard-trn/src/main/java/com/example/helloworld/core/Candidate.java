package com.example.helloworld.core;


import java.security.Principal;

public class Candidate implements Principal {
    private final String name;

    public Candidate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return (int) (Math.random() * 100);
    }
}
