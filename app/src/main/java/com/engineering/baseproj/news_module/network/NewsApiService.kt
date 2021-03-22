package com.engineering.baseproj.news_module.network

import com.engineering.baseproj.news_module.models.NewsMainModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    /**
     * Get top headlines from the API
     */

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): NewsMainModel
}