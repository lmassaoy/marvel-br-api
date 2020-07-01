package com.lyamada.domain;

import org.json.JSONArray;
import org.json.JSONObject;

public class MarvelCharacter {
    private int marvelId;
    private String name;
    private String description;

    public MarvelCharacter() {
        this.name="Herói não encontrado";
    }

    public MarvelCharacter(String responseBody){
        this.description = responseBody;
    }

    public MarvelCharacter(int marvelId, String name, String description) {
        this.marvelId = marvelId;
        this.name = name;
        this.description = description;
    }

    public static MarvelCharacter createChar(String response) {
        JSONArray results = new JSONObject(response).getJSONObject("data").getJSONArray("results");
        if (results.length()==1) {
            JSONObject characterFound = new JSONObject(response).getJSONObject("data").getJSONArray("results").getJSONObject(0);
            return new MarvelCharacter(characterFound.getInt("id"),characterFound.getString("name"),characterFound.getString("description"));
        }
        if (results.length()==0) {
            return new MarvelCharacter();
        } else {
            return null;
        }
    }

//    public MarvelCharacter getByName(String responseBody){
//        return new MarvelCharacter(responseBody);
//    }

    public int getMarvelId() {
        return marvelId;
    }

    public void setMarvelId(int marvelId) {
        this.marvelId = marvelId;
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
}
