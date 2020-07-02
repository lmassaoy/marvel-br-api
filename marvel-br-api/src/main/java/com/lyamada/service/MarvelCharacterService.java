package com.lyamada.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey="marvel-character-api")
public interface MarvelCharacterService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    String getByName(@QueryParam("name") String name, @QueryParam("nameStartsWith") String nameStartsWith,
                     @QueryParam("limit") int limit);

    @GET
    @Path("/{characterId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    String getById(@PathParam("characterId") int characterId);

}
