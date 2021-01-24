package com.koombea.ApiClients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.koombea.ApiClients.Base.BaseApiClient;
import com.koombea.ApiResponseObjects.LocationApi.AllLocations;
import com.koombea.ApiResponseObjects.LocationApi.Location;

import java.lang.reflect.Type;
import java.util.List;

public class LocationApiClient extends BaseApiClient {
    public LocationApiClient(){
        super("location/");
    }

    public AllLocations GetAllLocations(){
        return Response.as(AllLocations.class);
    }

    public Location GetLocation() {
        return Response.as(Location.class);
    }

    public List<Location> GetMultipleLocations() {
        Type type = new TypeReference<List<Location>>(){}.getType();
        return Response.as(type);
    }
}
