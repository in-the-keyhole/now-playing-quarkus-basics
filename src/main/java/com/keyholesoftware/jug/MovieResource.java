package com.keyholesoftware.jug;

import java.util.Set;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/movies")
public class MovieResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Set<Movie> nowPlaying() {
        return Set.of(new Movie("The Hobbit", "The ..."), new Movie("The Ring", "The..."));
    }
}
