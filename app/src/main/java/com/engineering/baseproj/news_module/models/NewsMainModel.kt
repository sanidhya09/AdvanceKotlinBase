package com.engineering.baseproj.news_module.models

data class NewsMainModel(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticlesModel>
)