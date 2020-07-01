package com.lyamada.service;

import com.lyamada.domain.MarvelCharacter;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey="marvel-character-api")
public interface MarvelCharacterService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    String getByName(@QueryParam("name") String name);

}
