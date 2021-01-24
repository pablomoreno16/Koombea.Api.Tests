package com.koombea.DataProviders;

import org.testng.annotations.DataProvider;

import java.util.HashMap;

public class EpisodeDataProvider {

    @DataProvider(name="GetAllEpisodesValidations200")
    public static Object[][] GetAllEpisodes200(){
        return new Object[][]{
                {""},
                {"-1"},
                {"0"},
                {"1"},
                {"3"},
                {"*"},
                {"2.5"}
        };
    }

    @DataProvider(name="GetAllEpisodesNegativeValidations")
    public static Object[][] GetAllEpisodesNegativeValidations(){
        return new Object[][]{
                {"4", 404, "There is nothing here"},
                {"0.9", 500, "Skip value must be non-negative, but received: -2"},
                {"0.1", 500, "Skip value must be non-negative, but received: -18"}
        };
    }

    @DataProvider(name="GetEpisodeValidations200")
    public static Object[][] GetEpisode200(){
        return new Object[][]{
                {"5", "Meeseeks and Destroy"},
                {"1", "Pilot"}
        };
    }

    @DataProvider(name="GetEpisodeNegativeValidations")
    public static Object[][] GetEpisodeNegativeValidations(){
        return new Object[][]{
                {"0", 404, "Episode not found"},
                {"-10", 404, "Episode not found"},
                {"5.5", 404, "Episode not found"},
                {"42", 404, "Episode not found"},
                {"*", 500, "Hey! you must provide an id"},
                {"2A", 500, "Cast to Number failed for value \"2A\" at path \"id\" for model \"Episode\""},
        };
    }

    @DataProvider(name="GetEpisodesWithFilters")
    public static Object[][] GetEpisodesWithFilters() {
        return new Object[][]{
                {new HashMap<String, String>() {{
                    put("name", "Morty");
                }}},
                {new HashMap<String, String>() {{
                    put("episode", "S01");
                }}},
                {new HashMap<String, String>() {{
                    put("name", "Morty");
                    put("episode", "S04");
                }}
                }
        };
    }
}
