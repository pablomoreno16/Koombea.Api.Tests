package com.koombea.DataProviders;

import org.testng.annotations.DataProvider;

public class CharacterDataProvider {

    @DataProvider(name="GetAllCharactersValidations200")
    public static Object[][] GetAllCharacters200(){
        return new Object[][]{
                {""},
                {"-1"},
                {"0"},
                {"1"},
                {"34"},
                {"*"},
                {"3.5"},
                {"20.8"}
        };
    }

    @DataProvider(name="GetAllCharactersNegativeValidations")
    public static Object[][] GetAllCharactersNegativeValidations(){
        return new Object[][]{
                {"35", 404, "There is nothing here"},
                {"0.5", 500, "Skip value must be non-negative, but received: -10"},
                {"0.01", 500, "Skip value must be non-negative, but received: -19"}
        };
    }

    @DataProvider(name="GetCharacterValidations200")
    public static Object[][] GetCharacters200(){
        return new Object[][]{
                {"5", "Jerry Smith"},
                {"1", "Rick Sanchez"}
        };
    }

    @DataProvider(name="GetCharacterNegativeValidations")
    public static Object[][] GetCharacterNegativeValidations(){
        return new Object[][]{
                {"0", 404, "Character not found"},
                {"-10", 404, "Character not found"},
                {"5.5", 404, "Character not found"},
                {"672", 404, "Character not found"},
                {"*", 500, "Hey! you must provide an id"},
                {"1M", 500, "Cast to Number failed for value \"1M\" at path \"id\" for model \"Character\""},
        };
    }
}
