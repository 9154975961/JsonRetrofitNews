package com.example.jsonretrofitnews.api;

import com.example.jsonretrofitnews.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessagesApi {

    @GET("top-headlines")
    Call<Message> getMessages(@Query("country") String country, @Query("apiKey") String apiKey);
}
