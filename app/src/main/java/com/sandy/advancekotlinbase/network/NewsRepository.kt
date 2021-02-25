package com.sandy.advancekotlinbase.network

class NewsRepository(private var newsApiService: NewsApiService) {

    suspend fun getTopHeadlinesSuspended(country: String, category: String, apiKey: String) =
        newsApiService.getTopHeadlinesSuspended(country, category, apiKey)

}