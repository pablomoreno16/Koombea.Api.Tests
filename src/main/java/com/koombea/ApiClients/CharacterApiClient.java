package com.koombea.ApiClients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.koombea.ApiClients.Base.BaseApiClient;
import com.koombea.ApiResponseObjects.CharacterApi.AllCharacters;
import com.koombea.ApiResponseObjects.CharacterApi.Character;

import java.lang.reflect.Type;
import java.util.List;

public class CharacterApiClient extends BaseApiClient {

    public CharacterApiClient(){
        super("character/");
    }

    public AllCharacters GetAllCharacters(){
        return Response.as(AllCharacters.class);
    }

    public Character GetCharacter() {
        return Response.as(Character.class);
    }

    public List<Character> GetMultipleCharacters() {
        Type type = new TypeReference<List<Character>>(){}.getType();
        return Response.as(type);
    }
}
