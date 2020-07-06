package com.lyamada;

import com.lyamada.service.DownloadMarvelDBService;
import com.lyamada.utils.DownloadMarvelDBHelper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class APIResource {
    @Inject
    Pool client;

    @Inject
    @RestClient
    DownloadMarvelDBService downloadMarvelDBService;

    @GET
    @Path("download")
    public Uni<Response> downloadMarvelDB() {
        DownloadMarvelDBHelper downloadMarvelDBHelper = new DownloadMarvelDBHelper(downloadMarvelDBService);
        int loops = downloadMarvelDBHelper.getLoopsNeeded(downloadMarvelDBHelper.getHeroesCount());
        downloadMarvelDBHelper.saveIntoDB(client, loops);

        return Uni.createFrom().item(Response.ok().build());
    }

}
