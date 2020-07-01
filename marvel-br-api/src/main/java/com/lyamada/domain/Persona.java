package main.java.com.lyamada.domain;

import org.json.JSONObject;

public class Persona {
    private int id;
    private int marvelId;
    private String name;
    private String description;
    private String thumbnailPath;
    private String thumbnailExt;
    private String marvelURI;

    public Persona() {}

    public Persona(int marvelId, String name, String description,
                String thumbnailPath, String thumbnailExt, String marvelURI) {
        this.marvelId = marvelId;
        this.name = name;
        this.description = description;
        this.thumbnailPath = thumbnailPath;
        this.thumbnailExt = thumbnailExt;
        this.marvelURI = marvelURI;
    }

    public Persona(int id, int marvelId, String name, String description,
                    String thumbnailPath, String thumbnailExt, String marvelURI) {
        this.id = id;
        this.marvelId = marvelId;
        this.name = name;
        this.description = description;
        this.thumbnailPath = thumbnailPath;
        this.thumbnailExt = thumbnailExt;
        this.marvelURI = marvelURI;
    }

    public static Persona from(JSONObject body) {
        return new Persona(body.getInt("id"),body.getString("name"),
                            body.getString("description"),
                            body.getJSONObject("thumbnail").getString("path"),
                            body.getJSONObject("thumbnail").getString("extension"),
                            body.getString("resourceURI"));
    }

}