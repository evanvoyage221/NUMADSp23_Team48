package edu.northeastern.numadsp23_team48.client;

import edu.northeastern.numadsp23_team48.model.CatFacts;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FetchData {

    @GET("/")
    Call<CatFacts> getAllData(@Query("count") Integer cnt);
}
