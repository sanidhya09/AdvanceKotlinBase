package com.sandy.advancekotlinbase.news_module.network

import com.sandy.advancekotlinbase.news_module.models.NewsMainModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    /**
     * Get top headlines from the API
     */

    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesSuspended(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): NewsMainModel
}