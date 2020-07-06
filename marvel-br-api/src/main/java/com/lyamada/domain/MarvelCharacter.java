package com.lyamada.domain;

import com.lyamada.utils.TranslationHelper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.stream.StreamSupport;

public class MarvelCharacter {
    private int marvelId;
    private int characterId;
    private String name;
    private String description;
    private String translatedDescription;
    private String thumbnail;
    private String wikiPage;

    public MarvelCharacter() {}

    public MarvelCharacter(int marvelId, String name, String description,
                           String translatedDescription, String thumbnail, String wikiPage) {
        this.marvelId = marvelId;
        this.name = name;
        this.description = description;
        this.translatedDescription = translatedDescription;
        this.thumbnail = thumbnail;
        this.wikiPage = wikiPage;
    }

    public MarvelCharacter(int marvelId, int characterId, String name, String description,
                           String translatedDescription, String thumbnail, String wikiPage) {
        this.marvelId = marvelId;
        this.characterId = characterId;
        this.name = name;
        this.description = description;
        this.translatedDescription = translatedDescription;
        this.thumbnail = thumbnail;
        this.wikiPage = wikiPage;
    }

    public static MarvelCharacter of(JSONObject characterObject) {
        String thumbnail = characterObject.getJSONObject("thumbnail").getString("path")
                            +"."
                            +characterObject.getJSONObject("thumbnail").getString("extension");
        JSONArray urls = characterObject.getJSONArray("urls");
        String wikiPage = "";
        for (int i=0; i<urls.length(); i++) {
            if (urls.getJSONObject(i).getString("type").equals("wiki")) {
                wikiPage = urls.getJSONObject(i).getString("url");
            }
        }

        return new MarvelCharacter(
                characterObject.getInt("id"),
                characterObject.getString("name"),
                characterObject.getString("description"),
                TranslationHelper.translate(characterObject.getString("description")),
                thumbnail,
                wikiPage);
    }

    public static MarvelCharacter from(Row row) {
        return new MarvelCharacter(row.getInteger("marvelId"),
                                    row.getInteger("characterId"),
                                    row.getString("name"),
                                    row.getString("description"),
                                    row.getString("translatedDescription"),
                                    row.getString("thumbnail"),
                                    row.getString("wikiPage"));
    }

//    Getters abd Setters
    public String getTranslatedDescription() {
        return translatedDescription;
    }

    public void setTranslatedDescription(String translatedDescription) {
        this.translatedDescription = translatedDescription;
    }

    public int getMarvelId() {
        return marvelId;
    }

    public void setMarvelId(int marvelId) {
        this.marvelId = marvelId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getWikiPage() {
        return wikiPage;
    }

    public void setWikiPage(String wikiPage) {
        this.wikiPage = wikiPage;
    }

//    CRUD methods
    public static Multi<MarvelCharacter> findByName(Pool client, String name) {
        String myQuery = String.format("select * from MarvelCharacters where name = \"%s\"", name);
        return client.query(myQuery)
                .execute()
                .onItem()
                .produceMulti(set -> Multi.createFrom().items(() -> StreamSupport.stream(set.spliterator(), false)))
                .onItem().apply(MarvelCharacter::from);
    }

    public static Uni<MarvelCharacter> findByMarvelId(Pool client, int id) {
        String myQuery = String.format("select * from MarvelCharacters where marvelId = %d", id);
        return client.query(myQuery)
                .execute()
                .onItem().apply(RowSet::iterator)
                .onItem().apply(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public static Multi<MarvelCharacter> findByNameStartsWith(Pool client, String nameStartsWith) {
        String myQuery = String.format("select * from MarvelCharacters where name like \"%s\"", nameStartsWith+"%");
        return client.query(myQuery)
                .execute()
                .onItem()
                .produceMulti(set -> Multi.createFrom().items(() -> StreamSupport.stream(set.spliterator(), false)))
                .onItem().apply(MarvelCharacter::from);
    }

    public static Uni<Integer> save(Pool client, MarvelCharacter character) {
        String myQuery = String.format("insert into MarvelCharacters values (null,%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\");",
                                        character.getMarvelId(),
                                        character.getName().replace("\"","'"),
                                        character.getDescription().replace("\"","'"),
                                        character.getTranslatedDescription().replace("\"","'"),
                                        character.getThumbnail(),
                                        character.getWikiPage());
        return client.query(myQuery)
                .execute()
                .onItem().apply(RowSet::iterator)
                .onItem().apply(iterator -> iterator.hasNext() ? iterator.next().getInteger("characterId") : null);
    }

    public static Uni<Integer> udpate(Pool client, MarvelCharacter newCharacter) {
        String myQuery = String.format("update MarvelCharacters set name = \"%s\", description = \"%s\", translatedDescription = \"%s\", thumbnail = \"%s\", wikipage = \"%s\" where marvelId = %d;",
                newCharacter.getName().replace("\"","'"),
                newCharacter.getDescription().replace("\"","'"),
                newCharacter.getTranslatedDescription().replace("\"","'"),
                newCharacter.getThumbnail(),
                newCharacter.getWikiPage(),
                newCharacter.getMarvelId());
        return client.query(myQuery)
                .execute()
                .onItem().apply(RowSet::iterator)
                .onItem().apply(iterator -> iterator.hasNext() ? iterator.next().getInteger("characterId") : null);
    }

}
