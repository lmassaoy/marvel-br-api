package com.lyamada.dto;

import com.lyamada.domain.MarvelCharacter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.ArrayList;
import java.util.List;

@JsonbPropertyOrder({ "marvelId", "name", "description", "translatedDescription", "thumbnail", "wikiPage" })
public class MarvelCharacterDTO {
    @JsonbProperty("marvelId")
    private int marvelId;
    @JsonbProperty("nome")
    private String name;
    @JsonbProperty("descricaoEN")
    private String description;
    @JsonbProperty("descricaoPT")
    private String translatedDescription;
    @JsonbProperty("foto")
    private String thumbnail;
    @JsonbProperty("wiki")
    private String wikiPage;

    public MarvelCharacterDTO() {
    }

    public MarvelCharacterDTO(MarvelCharacter character) {
        this.marvelId = character.getMarvelId();
        this.name = character.getName();
        this.description = character.getDescription();
        this.translatedDescription = character.getTranslatedDescription();
        this.thumbnail = character.getThumbnail();
        this.wikiPage = character.getWikiPage();
    }

    public static MarvelCharacterDTO of(MarvelCharacter character) {
        return new MarvelCharacterDTO(character);
    }

    public static List<MarvelCharacterDTO> ofList(List<MarvelCharacter> characterList) {
        List<MarvelCharacterDTO> dtoList = new ArrayList<>();
        for (MarvelCharacter character : characterList) {
            dtoList.add(of(character));
        }
        return dtoList;
    }



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

    public String getTranslatedDescription() {
        return translatedDescription;
    }

    public void setTranslatedDescription(String translatedDescription) {
        this.translatedDescription = translatedDescription;
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
}
