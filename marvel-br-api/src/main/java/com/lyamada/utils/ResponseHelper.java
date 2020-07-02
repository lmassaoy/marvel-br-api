package com.lyamada.utils;

import com.lyamada.domain.MarvelCharacter;
import com.lyamada.dto.MarvelCharacterDTO;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.core.Response;
import java.util.List;

public class ResponseHelper {

    public static Uni<Response> getResponseFromCharacterList(List<MarvelCharacter> resultList) {
        Uni<Response> response = Uni.createFrom()
            .item(resultList)
            .onItem()
            .apply(list -> list.isEmpty()==false ?  Response.ok(MarvelCharacterDTO.ofList(list)) : Response.status(Response.Status.NOT_FOUND))
            .onItem().apply(Response.ResponseBuilder::build);
        return response;
    }


}
