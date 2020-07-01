package com.lyamada;

import com.lyamada.domain.MarvelCharacter;
import com.lyamada.service.MarvelCharacterService;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

@Path("character")
@ApplicationScoped
public class MarvelCharacterResource {

    @Inject
    @RestClient
    MarvelCharacterService marvelCharacterService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public MarvelCharacter name(@QueryParam("name") String name) {
        return MarvelCharacter.createChar(marvelCharacterService.getByName(name));
    }
}
