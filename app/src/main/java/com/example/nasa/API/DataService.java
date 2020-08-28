package com.example.nasa.API;

import com.example.nasa.ImgModel.Asset;
import com.example.nasa.Model.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {
    @GET("search")
    Call<Search> getSearch(@Query("q") String query);

    @GET("asset/{id}")
    Call<Asset> getItem(@Path("id") String id);
}
