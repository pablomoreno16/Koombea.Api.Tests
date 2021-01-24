package com.koombea.Tests;

import com.koombea.ApiClients.CharacterApiClient;
import com.koombea.ApiResponseObjects.CharacterApi.AllCharacters;
import com.koombea.ApiResponseObjects.CharacterApi.Character;
import com.koombea.ApiResponseObjects.Common.ErrorResponse;
import com.koombea.DataProviders.CharacterDataProvider;
import org.apache.commons.lang3.math.NumberUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CharacterTests {

    @Test(dataProvider="GetAllCharactersValidations200", dataProviderClass = CharacterDataProvider.class)
    public void GetAllCharactersValidations200(String page) {
        CharacterApiClient characterApi = new CharacterApiClient();
        characterApi.CallApi(page, null);
        //Validate status code
        Assert.assertEquals(characterApi.GetStatusCode(), 200, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(characterApi.GetResponseHeader("Content-Type").contains("application/json"), "Verify response content-type");
        //Validate Response payload
        AllCharacters allCharacters = characterApi.GetAllCharacters();
        Assert.assertTrue(allCharacters.getInfo().getCount() > 0, "Verify count value");
        Assert.assertTrue(allCharacters.getInfo().getPages() > 0, "Verify pages value");
        double pageUsedForValidation;
        //check prev and next fields
        if(NumberUtils.isCreatable(page)) {
            pageUsedForValidation = Double.parseDouble(page);
            if(pageUsedForValidation < 1) pageUsedForValidation = 1;
            int pageInt = (int)Double.parseDouble(page);
            if (pageInt <= 1) {
                Assert.assertNull(allCharacters.getInfo().getPrev(),"Prev URL is null for fist element");
            }else if (pageInt < allCharacters.getInfo().getPages()) {
                Assert.assertTrue(allCharacters.getInfo().getPrev().contains((pageInt-1) + ""), "Verify Prev value");
                Assert.assertTrue(allCharacters.getInfo().getNext().contains((pageInt+1) + ""), "Verify Next Value");
            }else {
                Assert.assertTrue(allCharacters.getInfo().getPrev().contains((pageInt-1) + ""), "Verify Prev value");
                Assert.assertNull(allCharacters.getInfo().getNext(), "Next URL is null for last element");
            }
        }else{
            pageUsedForValidation = 1;
            Assert.assertNull(allCharacters.getInfo().getPrev(), "Prev URL is null for fist element");
        }
        Assert.assertTrue(allCharacters.getResults().size() > 0, "Verify results value");

        int idExpected = (int)((pageUsedForValidation-1)*20)+1;
        Assert.assertEquals(allCharacters.getResults().get(0).getId(), idExpected, "Verify first character id");
    }

    @Test(dataProvider="GetAllCharactersNegativeValidations", dataProviderClass = CharacterDataProvider.class)
    public void GetAllCharactersNegativeValidations(String page, int statusCode, String errorMessage) {
        CharacterApiClient characterApi = new CharacterApiClient();
        characterApi.CallApi(page, null);
        //Validate status code
        Assert.assertEquals(characterApi.GetStatusCode(), statusCode, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(characterApi.GetResponseHeader("Content-Type").contains("application/json"), "Verify response content-type");
        //Validate Response payload
        ErrorResponse errorResponse = characterApi.GetErrorResponse();
        Assert.assertEquals(errorResponse.getError(), errorMessage, "Verify error message");
    }

    @Test(dataProvider="GetCharacterValidations200", dataProviderClass = CharacterDataProvider.class)
    public void GetCharacterValidations200(String id, String name) {
        CharacterApiClient characterApi = new CharacterApiClient();
        characterApi.CallApi(null, id);
        //Validate status code
        Assert.assertEquals(characterApi.GetStatusCode(), 200, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(characterApi.GetResponseHeader("Content-Type").contains("application/json"), "Response should be a json");
        //Validate Response payload
        Character character = characterApi.GetCharacter();
        Assert.assertEquals(character.getId(), Integer.parseInt(id), "Verify character id");
        Assert.assertEquals(character.getName(), name, "Verify character name");
        Assert.assertTrue(character.getStatus().length() > 0, "Verify Status");
        Assert.assertTrue(character.getSpecies().length() > 0, "Verify Species");
        Assert.assertTrue(character.getGender().length() > 0, "Verify Gender");
        Assert.assertTrue(character.getOrigin().getName().length() > 0, "Verify Origin Name");
        Assert.assertTrue(character.getOrigin().getUrl().length() > 0, "Verify Origin Url");
        Assert.assertTrue(character.getLocation().getName().length() > 0, "Verify Location Name");
        Assert.assertTrue(character.getLocation().getUrl().length() > 0, "Verify Location Url");
        Assert.assertTrue(character.getImage().contains("character/avatar/" + id + ".jpeg"), "Verify character image url");
        Assert.assertTrue(character.getEpisode().size() > 0, "Verify Episode list");
        Assert.assertTrue(character.getUrl().contains("character/" + id), "Verify character url");
        Assert.assertTrue(character.getCreated().length() > 0, "Verify Created");
    }

    @Test(dataProvider="GetCharacterNegativeValidations", dataProviderClass = CharacterDataProvider.class)
    public void GetCharacterNegativeValidations(String id, int statusCode, String errorMessage) {
        CharacterApiClient characterApi = new CharacterApiClient();
        characterApi.CallApi(null, id);
        //Validate status code
        Assert.assertEquals(characterApi.GetStatusCode(), statusCode, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(characterApi.GetResponseHeader("Content-Type").contains("application/json"), "Response should be a json");
        //Validate Response payload
        ErrorResponse errorResponse = characterApi.GetErrorResponse();
        Assert.assertEquals(errorResponse.getError(), errorMessage, "Verify error message");
    }

}
