package com.example.nasa.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRetrofit {
    private static Retrofit retrofit;
    private static final String BASE_URL_SEARCH = "https://images-api.nasa.gov/";

    public static Retrofit getSearchRetrofit(){
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL_SEARCH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
