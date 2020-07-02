package com.lyamada;

import com.lyamada.domain.MarvelCharacter;
import com.lyamada.dto.MarvelCharacterDTO;
import com.lyamada.service.MarvelCharacterService;
import com.lyamada.utils.JsonHelper;
import com.lyamada.utils.ResponseHelper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Optional;


@Path("character")
public class MarvelCharacterResource {

    @Inject
    Pool client;

    @Inject
    @RestClient
    MarvelCharacterService marvelCharacterService;

    @GET
    @Path("{marvelId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> getById(@PathParam("marvelId") int marvelId) {
        if (Optional.ofNullable(marvelId).orElse(0) != 0) {
            Uni<Response> response = MarvelCharacter.findByMarvelId(client, marvelId)
                                                        .onItem()
                                                        .apply(character -> character != null ?
                                                                Response.ok(MarvelCharacterDTO.of(character)) :
                                                                Response.status(Status.NOT_FOUND))
                                                        .onItem().apply(ResponseBuilder::build);
            if (response.onItem().apply(Response::getStatus).await().indefinitely() == 200) {
                return response;
        } else {
            String marvelResponse = marvelCharacterService.getById(marvelId);
            int marvelResponseStatusCode = new JSONObject(marvelResponse).getInt("code");

//              TODO
//                For now it's working the auto return from Marvel API with 404 response. In a future
//                I need to think in a way to handle this. Or don't do it.
//                if(marvelResponseStatusCode == 404) {
//                  return Uni.createFrom().item(Response.status(Status.NOT_FOUND).build());
//                }

            if(marvelResponseStatusCode == 200) {
                MarvelCharacter character = MarvelCharacter.of(JsonHelper.getFirstResult(marvelResponse));
                Integer resultInsert = MarvelCharacter.save(client, character).await().indefinitely();
                return Uni.createFrom().item(character)
                        .onItem()
                        .apply(marvelCharacter -> marvelCharacter != null ?
                                Response.ok(MarvelCharacterDTO.of(character)) :
                                Response.status(Status.NOT_FOUND))
                        .onItem().apply(ResponseBuilder::build);
                } else {
                    return Uni.createFrom().item(Response.status(marvelResponseStatusCode).build());
                }
            }
        }
        return Uni.createFrom().item(Response.status(400,"Missing Parameters").build());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> getByName(@QueryParam("name") String name, @QueryParam("nameStartsWith") String nameStartsWith,
                                   @QueryParam("limit") int limit) {
        if (name!=null) {
            List<MarvelCharacter> resultList = MarvelCharacter.findByName(client, name)
                    .onItem().apply(row -> row)
                    .collectItems().asList().await().indefinitely();
            Uni<Response> response = ResponseHelper.getResponseFromCharacterList(resultList);
            if (resultList.size()>0) {
                return response;
            } else {
                String marvelResponse = marvelCharacterService.getByName(name,null, 20);
                int numberOfResults = new JSONObject(marvelResponse).getJSONObject("data").getInt("count");
                JSONArray resultsFound = JsonHelper.getResultArray(marvelResponse);

                if(numberOfResults > 0) {
                    for (int i=0; i<resultsFound.length(); i++) {
                        MarvelCharacter newCharacter = MarvelCharacter.of(resultsFound.getJSONObject(i));
                        Integer resultInsert = MarvelCharacter.save(client, newCharacter).await().indefinitely();
                        resultList.add(newCharacter);
                    }
                    return ResponseHelper.getResponseFromCharacterList(resultList);
                }
                return Uni.createFrom().item(Response.status(404).build());
            }
        }
        if (nameStartsWith!=null) {
            String marvelResponse = marvelCharacterService.getByName(null,nameStartsWith, 30);
            JSONArray resultsFound = JsonHelper.getResultArray(marvelResponse);
            List<MarvelCharacter> resultList = MarvelCharacter.findByNameStartsWith(client, nameStartsWith)
                    .onItem().apply(row -> row)
                    .collectItems().asList().await().indefinitely();

            if (resultList.isEmpty()) {
                if(resultsFound.length()>0) {
                    for (int i=0; i<resultsFound.length(); i++) {
                        MarvelCharacter newCharacter = MarvelCharacter.of(resultsFound.getJSONObject(i));
                        Integer resultInsert = MarvelCharacter.save(client, newCharacter).await().indefinitely();
                        resultList.add(newCharacter);
                    }
                    return ResponseHelper.getResponseFromCharacterList(resultList);
                } else {
                    return Uni.createFrom().item(Response.status(404).build());
                }
            } else {
                if (resultList.size()==resultsFound.length()) {
                    return ResponseHelper.getResponseFromCharacterList(resultList);
                } else {
                    resultList.clear();
                    for (int i=0; i<resultsFound.length(); i++) {
                        MarvelCharacter newCharacter = MarvelCharacter.of(resultsFound.getJSONObject(i));
                        MarvelCharacter validatedMarvelId = MarvelCharacter
                                                                .findByMarvelId(client,newCharacter.getMarvelId())
                                                                .await().indefinitely();
                        if (validatedMarvelId == null) {
                            Integer resultInsert = MarvelCharacter.save(client, newCharacter).await().indefinitely();
                            resultList.add(newCharacter);
                        } else {
                            Integer resultInsert = MarvelCharacter.udpate(client, newCharacter).await().indefinitely();
                            resultList.add(validatedMarvelId);
                        }
                    }
                    return ResponseHelper.getResponseFromCharacterList(resultList);
                }
            }
        } else {
            return Uni.createFrom().item(Response.status(400,"Missing Parameters").build());
        }
    }

//    Marvel original response

    @GET
    @Path("marvelResponse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String name(@QueryParam("name") String name, @QueryParam("nameStartsWith") String nameStartsWith) {
        if (name!=null) {
            return marvelCharacterService.getByName(name,nameStartsWith,20);
        }
        if (nameStartsWith!=null) {
            return marvelCharacterService.getByName(null,nameStartsWith,30);
        } else {
            return marvelCharacterService.getByName(null,null,20);
        }
    }

    @GET
    @Path("marvelResponse/{marvelId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String name(@PathParam("marvelId") int marvelId) {
        return marvelCharacterService.getById(marvelId);
    }
}
