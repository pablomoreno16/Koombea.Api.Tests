package com.koombea.Tests;

import com.koombea.ApiClients.CharacterApiClient;
import com.koombea.ApiResponseObjects.CharacterApi.AllCharacters;
import com.koombea.ApiResponseObjects.CharacterApi.Character;
import com.koombea.ApiResponseObjects.Common.ErrorResponse;
import com.koombea.DataProviders.CharacterDataProvider;
import com.koombea.Utils.Utils;
import org.apache.commons.lang3.math.NumberUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

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
                System.out.println(allCharacters.getInfo().getPrev() + " - " + pageInt);
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
        characterApi.CallApi("", id);
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
        characterApi.CallApi("", id);
        //Validate status code
        Assert.assertEquals(characterApi.GetStatusCode(), statusCode, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(characterApi.GetResponseHeader("Content-Type").contains("application/json"), "Response should be a json");
        //Validate Response payload
        ErrorResponse errorResponse = characterApi.GetErrorResponse();
        Assert.assertEquals(errorResponse.getError(), errorMessage, "Verify error message");
    }

    @Test
    public void GetMultipleCharacters(){
        CharacterApiClient characterApi = new CharacterApiClient();
        int bound = 671;
        int size = Utils.NextInt(bound);
        int[] items = Utils.GetArrayWithRandomNumbersNoRepeat(size, bound, false, true);
        System.out.println(Arrays.toString(items));
        characterApi.CallApi("", Arrays.toString(items));
        //Validate status code
        Assert.assertEquals(characterApi.GetStatusCode(), 200, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(characterApi.GetResponseHeader("Content-Type").contains("application/json"), "Response should be a json");
        //Validate Response payload
        List<Character> characters = characterApi.GetMultipleCharacters();
        Assert.assertEquals(characters.size(), items.length, "Verify the quantity of characters.");
        for (Character character : characters) {
            Assert.assertTrue(Utils.ArrayContainsNumber(items, character.getId()), "Verify id [" + character.getId() + "] is contained in the filters " + Arrays.toString(items));
        }
    }

    @Test(dataProvider="GetCharactersWithFilters", dataProviderClass = CharacterDataProvider.class)
    public void GetCharactersWithFilters(Map<String,String> filters){
        CharacterApiClient characterApi = new CharacterApiClient();
        characterApi.CallApi(filters, null);
        //Validate status code
        Assert.assertEquals(characterApi.GetStatusCode(), 200, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(characterApi.GetResponseHeader("Content-Type").contains("application/json"), "Response should be a json");
        //Validate Response payload
        List<Character> characters = characterApi.GetAllCharacters().getResults();
        Set keys = filters.keySet();
        Iterator iKeys = keys.iterator();
        while (iKeys.hasNext()) {
            String key = iKeys.next().toString();
            String filter = filters.get(key).toLowerCase(Locale.ROOT);
            for (Character character : characters) {
                String characterValue = "";
                if(key.equals("name"))
                    characterValue = character.getName();
                if(key.equals("status"))
                    characterValue = character.getStatus();
                if(key.equals("species"))
                    characterValue = character.getSpecies();
                if(key.equals("gender"))
                    characterValue = character.getGender();
                Assert.assertTrue(characterValue.toLowerCase(Locale.ROOT).contains(filter), "Verify character [" + characterValue + "] contains the filter [" + filter + "].");
            }
        }
    }

}