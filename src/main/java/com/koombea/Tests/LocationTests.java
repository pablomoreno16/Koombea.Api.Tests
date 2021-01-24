package com.koombea.Tests;

import com.koombea.ApiClients.LocationApiClient;
import com.koombea.ApiResponseObjects.Common.ErrorResponse;
import com.koombea.ApiResponseObjects.LocationApi.AllLocations;
import com.koombea.ApiResponseObjects.LocationApi.Location;
import com.koombea.DataProviders.LocationDataProvider;
import org.apache.commons.lang3.math.NumberUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LocationTests {

    @Test(dataProvider="GetAllLocationsValidations200", dataProviderClass = LocationDataProvider.class)
    public void GetAllLocationsValidations200(String page) {
        LocationApiClient locationApiClient = new LocationApiClient();
        locationApiClient.CallApi(page, null);
        //Validate status code
        Assert.assertEquals(locationApiClient.GetStatusCode(), 200, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(locationApiClient.GetResponseHeader("Content-Type").contains("application/json"), "Verify response content-type");
        //Validate Response payload
        AllLocations allLocations = locationApiClient.GetAllLocations();
        Assert.assertTrue(allLocations.getInfo().getCount() > 0, "Verify count value");
        Assert.assertTrue(allLocations.getInfo().getPages() > 0, "Verify pages value");
        double pageUsedForValidation;
        //check prev and next fields
        if(NumberUtils.isCreatable(page)) {
            pageUsedForValidation = Double.parseDouble(page);
            if(pageUsedForValidation < 1) pageUsedForValidation = 1;
            int pageInt = (int)Double.parseDouble(page);
            if (pageInt <= 1) {
                Assert.assertNull(allLocations.getInfo().getPrev(),"Prev URL is null for fist element");
            }else if (pageInt < allLocations.getInfo().getPages()) {
                Assert.assertTrue(allLocations.getInfo().getPrev().contains((pageInt-1) + ""), "Verify Prev value");
                Assert.assertTrue(allLocations.getInfo().getNext().contains((pageInt+1) + ""), "Verify Next Value");
            }else {
                Assert.assertTrue(allLocations.getInfo().getPrev().contains((pageInt-1) + ""), "Verify Prev value");
                Assert.assertNull(allLocations.getInfo().getNext(), "Next URL is null for last element");
            }
        }else{
            pageUsedForValidation = 1;
            Assert.assertNull(allLocations.getInfo().getPrev(), "Prev URL is null for fist element");
        }
        Assert.assertTrue(allLocations.getResults().size() > 0, "Verify results value");

        int idExpected = (int)((pageUsedForValidation-1)*20)+1;
        Assert.assertEquals(allLocations.getResults().get(0).getId(), idExpected, "Verify first location id");
    }

    @Test(dataProvider="GetAllLocationsNegativeValidations", dataProviderClass = LocationDataProvider.class)
    public void GetAllLocationsNegativeValidations(String page, int statusCode, String errorMessage) {
        LocationApiClient locationApiClient = new LocationApiClient();
        locationApiClient.CallApi(page, null);
        //Validate status code
        Assert.assertEquals(locationApiClient.GetStatusCode(), statusCode, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(locationApiClient.GetResponseHeader("Content-Type").contains("application/json"), "Verify response content-type");
        //Validate Response payload
        ErrorResponse errorResponse = locationApiClient.GetErrorResponse();
        Assert.assertEquals(errorResponse.getError(), errorMessage, "Verify error message");
    }

    @Test(dataProvider="GetLocationValidations200", dataProviderClass = LocationDataProvider.class)
    public void GetLocationValidations200(String id, String name) {
        LocationApiClient locationApiClient = new LocationApiClient();
        locationApiClient.CallApi(null, id);
        //Validate status code
        Assert.assertEquals(locationApiClient.GetStatusCode(), 200, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(locationApiClient.GetResponseHeader("Content-Type").contains("application/json"), "Response should be a json");
        //Validate Response payload
        Location location = locationApiClient.GetLocation();
        Assert.assertEquals(location.getId(), Integer.parseInt(id), "Verify location id");
        Assert.assertEquals(location.getName(), name, "Verify location name");
        Assert.assertTrue(location.getType().length() > 0, "Verify type");
        Assert.assertTrue(location.getDimension().length() > 0, "Verify Dimension");
        Assert.assertTrue(location.getResidents().size() > 0, "Verify Residents");
        Assert.assertTrue(location.getUrl().contains("location/" + id), "Verify location url");
        Assert.assertTrue(location.getCreated().length() > 0, "Verify Created");
    }

    @Test(dataProvider="GetLocationNegativeValidations", dataProviderClass = LocationDataProvider.class)
    public void GetLocationNegativeValidations(String id, int statusCode, String errorMessage) {
        LocationApiClient locationApiClient = new LocationApiClient();
        locationApiClient.CallApi(null, id);
        //Validate status code
        Assert.assertEquals(locationApiClient.GetStatusCode(), statusCode, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(locationApiClient.GetResponseHeader("Content-Type").contains("application/json"), "Response should be a json");
        //Validate Response payload
        ErrorResponse errorResponse = locationApiClient.GetErrorResponse();
        Assert.assertEquals(errorResponse.getError(), errorMessage, "Verify error message");
    }
}
