package com.sandy.advancekotlinbase.news_module.network

class NewsRemoteDataRepository(private var newsApiService: NewsApiService) {

    suspend fun getTopHeadlinesSuspended(country: String, category: String, apiKey: String) =
        newsApiService.getTopHeadlinesSuspended(country, category, apiKey)

}