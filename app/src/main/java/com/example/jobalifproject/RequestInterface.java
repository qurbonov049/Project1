package com.example.jobalifproject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RequestInterface {

    @GET("/service/v2/upcomingGuides/")
    Call<String> getJSON();
}
