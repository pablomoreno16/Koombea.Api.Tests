package com.koombea.Tests;

import com.koombea.ApiClients.EpisodeApiClient;
import com.koombea.ApiClients.LocationApiClient;
import com.koombea.ApiResponseObjects.Common.ErrorResponse;
import com.koombea.ApiResponseObjects.EpisodeApi.AllEpisodes;
import com.koombea.ApiResponseObjects.EpisodeApi.Episode;
import com.koombea.ApiResponseObjects.LocationApi.AllLocations;
import com.koombea.ApiResponseObjects.LocationApi.Location;
import com.koombea.DataProviders.EpisodeDataProvider;
import com.koombea.DataProviders.LocationDataProvider;
import org.apache.commons.lang3.math.NumberUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EpisodeTests {

    @Test(dataProvider="GetAllEpisodesValidations200", dataProviderClass = EpisodeDataProvider.class)
    public void GetAllCharactersValidations200(String page) {
        EpisodeApiClient episodeApiClient = new EpisodeApiClient();
        episodeApiClient.CallApi(page, null);
        //Validate status code
        Assert.assertEquals(episodeApiClient.GetStatusCode(), 200, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(episodeApiClient.GetResponseHeader("Content-Type").contains("application/json"), "Verify response content-type");
        //Validate Response payload
        AllEpisodes allEpisodes = episodeApiClient.GetAllEpisodes();
        Assert.assertTrue(allEpisodes.getInfo().getCount() > 0, "Verify count value");
        Assert.assertTrue(allEpisodes.getInfo().getPages() > 0, "Verify pages value");
        double pageUsedForValidation;
        //check prev and next fields
        if(NumberUtils.isCreatable(page)) {
            pageUsedForValidation = Double.parseDouble(page);
            if(pageUsedForValidation < 1) pageUsedForValidation = 1;
            int pageInt = (int)Double.parseDouble(page);
            if (pageInt <= 1) {
                Assert.assertNull(allEpisodes.getInfo().getPrev(),"Prev URL is null for fist element");
            }else if (pageInt < allEpisodes.getInfo().getPages()) {
                Assert.assertTrue(allEpisodes.getInfo().getPrev().contains((pageInt-1) + ""), "Verify Prev value");
                Assert.assertTrue(allEpisodes.getInfo().getNext().contains((pageInt+1) + ""), "Verify Next Value");
            }else {
                Assert.assertTrue(allEpisodes.getInfo().getPrev().contains((pageInt-1) + ""), "Verify Prev value");
                Assert.assertNull(allEpisodes.getInfo().getNext(), "Next URL is null for last element");
            }
        }else{
            pageUsedForValidation = 1;
            Assert.assertNull(allEpisodes.getInfo().getPrev(), "Prev URL is null for fist element");
        }
        Assert.assertTrue(allEpisodes.getResults().size() > 0, "Verify results value");

        int idExpected = (int)((pageUsedForValidation-1)*20)+1;
        Assert.assertEquals(allEpisodes.getResults().get(0).getId(), idExpected, "Verify first character id");
    }

    @Test(dataProvider="GetAllEpisodesNegativeValidations", dataProviderClass = EpisodeDataProvider.class)
    public void GetAllEpisodesNegativeValidations(String page, int statusCode, String errorMessage) {
        EpisodeApiClient episodeApiClient = new EpisodeApiClient();
        episodeApiClient.CallApi(page, null);
        //Validate status code
        Assert.assertEquals(episodeApiClient.GetStatusCode(), statusCode, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(episodeApiClient.GetResponseHeader("Content-Type").contains("application/json"), "Verify response content-type");
        //Validate Response payload
        ErrorResponse errorResponse = episodeApiClient.GetErrorResponse();
        Assert.assertEquals(errorResponse.getError(), errorMessage, "Verify error message");
    }

    @Test(dataProvider="GetEpisodeValidations200", dataProviderClass = EpisodeDataProvider.class)
    public void GetEpisodeValidations200(String id, String name) {
        EpisodeApiClient episodeApiClient = new EpisodeApiClient();
        episodeApiClient.CallApi(null, id);
        //Validate status code
        Assert.assertEquals(episodeApiClient.GetStatusCode(), 200, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(episodeApiClient.GetResponseHeader("Content-Type").contains("application/json"), "Response should be a json");
        //Validate Response payload
        Episode episode = episodeApiClient.GetEpisode();
        Assert.assertEquals(episode.getId(), Integer.parseInt(id), "Verify episode id");
        Assert.assertEquals(episode.getName(), name, "Verify episode name");
        Assert.assertTrue(episode.getAirDate().length() > 0, "Verify air-date");
        Assert.assertTrue(episode.getEpisode().length() > 0, "Verify episode");
        Assert.assertTrue(episode.getCharacters().size() > 0, "Verify Characters");
        Assert.assertTrue(episode.getUrl().contains("episode/" + id), "Verify episode url");
        Assert.assertTrue(episode.getCreated().length() > 0, "Verify Created");
    }

    @Test(dataProvider="GetEpisodeNegativeValidations", dataProviderClass = EpisodeDataProvider.class)
    public void GetLocationNegativeValidations(String id, int statusCode, String errorMessage) {
        EpisodeApiClient episodeApiClient = new EpisodeApiClient();
        episodeApiClient.CallApi(null, id);
        //Validate status code
        Assert.assertEquals(episodeApiClient.GetStatusCode(), statusCode, "Verify status code");
        //Validate Response headers
        Assert.assertTrue(episodeApiClient.GetResponseHeader("Content-Type").contains("application/json"), "Response should be a json");
        //Validate Response payload
        ErrorResponse errorResponse = episodeApiClient.GetErrorResponse();
        Assert.assertEquals(errorResponse.getError(), errorMessage, "Verify error message");
    }
}
