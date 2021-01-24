package com.koombea.ApiClients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.koombea.ApiClients.Base.BaseApiClient;
import com.koombea.ApiResponseObjects.EpisodeApi.AllEpisodes;
import com.koombea.ApiResponseObjects.EpisodeApi.Episode;

import java.lang.reflect.Type;
import java.util.List;

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

    public List<Episode> GetMultipleEpisodes() {
        Type type = new TypeReference<List<Episode>>(){}.getType();
        return Response.as(type);
    }
}
