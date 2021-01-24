package com.koombea.ApiClients.Base;

import com.koombea.ApiResponseObjects.Common.ErrorResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseApiClient {

    protected final String BaseUri = "https://rickandmortyapi.com/api/";
    protected String BasePath;
    protected Response Response;

    protected BaseApiClient(String path){
        BasePath = path;
    }

    public void CallApi(String page, String item){
        if(item != null && !item.isEmpty()){
            BasePath += item;
        }
        if(page != null && !page.isEmpty())
            Response = RestAssured.given()
                    .baseUri(BaseUri)
                    .basePath(BasePath)
                    .param("page", page)
                    .get().andReturn();
        else
            Response = RestAssured.given()
                    .baseUri(BaseUri)
                    .basePath(BasePath)
                    .get().andReturn();
        //Response.getBody().prettyPrint();
    }

    public int GetStatusCode() {
        return Response.getStatusCode();
    }

    public String GetResponseHeader(String header) {
        return Response.getHeader(header);
    }

    public ErrorResponse GetErrorResponse() {
        return Response.as(ErrorResponse.class);
    }
}
