package com.koombea.ApiClients;

import com.koombea.ApiClients.Base.BaseApiClient;
import com.koombea.ApiResponseObjects.CharacterApi.AllCharacters;
import com.koombea.ApiResponseObjects.CharacterApi.Character;

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
}
