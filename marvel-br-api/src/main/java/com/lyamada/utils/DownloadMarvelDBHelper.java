package com.lyamada.utils;

import com.lyamada.domain.MarvelCharacter;
import com.lyamada.service.DownloadMarvelDBService;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.RowSet;
import org.json.JSONArray;
import org.json.JSONObject;

public class DownloadMarvelDBHelper {
    private DownloadMarvelDBService downloadMarvelDBService;

    public DownloadMarvelDBHelper(DownloadMarvelDBService downloadMarvelDBService) {
        this.downloadMarvelDBService = downloadMarvelDBService;
    }

    public void saveIntoDB(Pool client, int loops) {
        System.out.println("This operation may take several minutes (11m average time)");
        int offset = 0;
        int limit = 20;
        JSONObject response;
        JSONArray resultArray;
        Integer insertResponse;

        truncateDB(client);

        for(int i = 0; i<loops; i++) {
            response = new JSONObject(this.downloadMarvelDBService.getByLoops(offset,limit));
            resultArray = response.getJSONObject("data").getJSONArray("results");
            int charactersResult = response.getJSONObject("data").getInt("count");

            for(int index = 0; index<resultArray.length(); index++) {
                insertResponse = MarvelCharacter
                                                .save(client, MarvelCharacter.of(resultArray.getJSONObject(index)))
                                                .await().indefinitely();
            }

            System.out.println("offset: "+offset+", limit: "+limit+", characters read and inserted: "+charactersResult);
            offset+=20;
        }
    }

    public void truncateDB(Pool client) {
        Integer insertResponse = client.query("truncate table marvel_db.MarvelCharacters")
                                        .execute()
                                        .onItem().apply(RowSet::iterator)
                                        .onItem()
                                        .apply(iterator -> iterator.hasNext() ? iterator.next().getInteger("characterId") : null)
                                        .await().indefinitely();
    }

    public int getHeroesCount() {
        String response = this.downloadMarvelDBService.getByLoops(0,1);
        return new JSONObject(response).getJSONObject("data").getInt("total");
    }

    public int getLoopsNeeded(int count) {
        int loopsNeeded = (count/20)+1;
        System.out.println("Loops needed: "+loopsNeeded);
        return loopsNeeded;
    }


}
