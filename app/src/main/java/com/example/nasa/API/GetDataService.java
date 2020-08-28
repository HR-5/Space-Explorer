package com.example.nasa.API;

import com.example.nasa.Model.Apod;
import com.example.nasa.ImgModel.Asset;
import com.example.nasa.Model.Search;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("planetary/apod/")
    Call<Apod> getApod(@Query("date") String date, @Query("api_key") String api_key);
}
