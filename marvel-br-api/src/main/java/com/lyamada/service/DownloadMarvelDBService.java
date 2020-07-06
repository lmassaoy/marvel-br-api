package com.lyamada.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey="marvel-character-api")
public interface DownloadMarvelDBService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    String getByLoops(@QueryParam("offset") int offset, @QueryParam("limit") int limit);

}
