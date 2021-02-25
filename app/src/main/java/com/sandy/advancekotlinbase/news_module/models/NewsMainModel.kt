package com.sandy.advancekotlinbase.news_module.models

data class NewsMainModel(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticlesModel>
)