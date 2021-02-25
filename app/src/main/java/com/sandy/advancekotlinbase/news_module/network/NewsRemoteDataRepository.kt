package com.sandy.advancekotlinbase.news_module.network

class NewsRemoteDataRepository(private var newsApiService: NewsApiService) {

    suspend fun getTopHeadlines(country: String, category: String, apiKey: String) =
        newsApiService.getTopHeadlines(country, category, apiKey)

}