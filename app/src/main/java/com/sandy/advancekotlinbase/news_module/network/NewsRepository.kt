package com.sandy.advancekotlinbase.news_module.network

class NewsRepository(private var newsApiService: NewsApiService) {

    suspend fun getTopHeadlinesSuspended(country: String, category: String, apiKey: String) =
        newsApiService.getTopHeadlinesSuspended(country, category, apiKey)

}