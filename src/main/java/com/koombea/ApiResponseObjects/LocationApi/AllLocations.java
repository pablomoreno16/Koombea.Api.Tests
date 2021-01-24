package com.koombea.ApiResponseObjects.LocationApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.koombea.ApiResponseObjects.Common.Info;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "info",
        "results"
})
public class AllLocations {

    @JsonProperty("info")
    private Info info;
    @JsonProperty("results")
    private List<Location> results = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("info")
    public Info getInfo() {
        return info;
    }

    @JsonProperty("info")
    public void setInfo(Info info) {
        this.info = info;
    }

    @JsonProperty("results")
    public List<Location> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Location> results) {
        this.results = results;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}