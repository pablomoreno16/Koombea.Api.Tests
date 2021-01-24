package com.koombea.ApiClients;

import com.koombea.ApiClients.Base.BaseApiClient;
import com.koombea.ApiResponseObjects.EpisodeApi.AllEpisodes;
import com.koombea.ApiResponseObjects.EpisodeApi.Episode;

public class EpisodeApiClient extends BaseApiClient {

    public EpisodeApiClient() {
        super("episode/");
    }

    public AllEpisodes GetAllEpisodes(){
        return Response.as(AllEpisodes.class);
    }

    public Episode GetEpisode() {
        return Response.as(Episode.class);
    }
}
