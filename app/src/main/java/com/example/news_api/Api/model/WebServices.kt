package com.example.news_api.Api.model

import com.example.news_api.Api.model.NewsResponse.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getSources(
        @Query("apikey") apikey: String,
        @Query("category") category: String,
    ):
            Call<SourcesResponse>

    @GET("v2/everything")
    fun getNews(
        @Query("apikey") apikey: String,
        @Query("sources") sources: String
    ): Call<NewsResponse>

}