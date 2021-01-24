package com.koombea.DataProviders;

import org.testng.annotations.DataProvider;

public class LocationDataProvider {

    @DataProvider(name="GetAllLocationsValidations200")
    public static Object[][] GetAllLocations200(){
        return new Object[][]{
                {""},
                {"-1"},
                {"0"},
                {"1"},
                {"6"},
                {"*"},
                {"3.5"}
        };
    }

    @DataProvider(name="GetAllLocationsNegativeValidations")
    public static Object[][] GetAllLocationsNegativeValidations(){
        return new Object[][]{
                {"7", 404, "There is nothing here"},
                {"0.9", 500, "Skip value must be non-negative, but received: -2"},
                {"0.1", 500, "Skip value must be non-negative, but received: -18"}
        };
    }

    @DataProvider(name="GetLocationValidations200")
    public static Object[][] GetLocation200(){
        return new Object[][]{
                {"5", "Anatomy Park"},
                {"1", "Earth (C-137)"}
        };
    }

    @DataProvider(name="GetLocationNegativeValidations")
    public static Object[][] GetLocationNegativeValidations(){
        return new Object[][]{
                {"0", 404, "Location not found"},
                {"-10", 404, "Location not found"},
                {"5.5", 404, "Location not found"},
                {"109", 404, "Location not found"},
                {"*", 500, "Hey! you must provide an id"},
                {"1A", 500, "Cast to Number failed for value \"1A\" at path \"id\" for model \"Location\""},
        };
    }
}
