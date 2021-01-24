package com.koombea.ApiClients;

import com.koombea.ApiClients.Base.BaseApiClient;
import com.koombea.ApiResponseObjects.LocationApi.AllLocations;
import com.koombea.ApiResponseObjects.LocationApi.Location;

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
}
