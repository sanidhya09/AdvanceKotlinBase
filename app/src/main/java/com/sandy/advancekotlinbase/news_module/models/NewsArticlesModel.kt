package com.sandy.advancekotlinbase.news_module.models

data class NewsArticlesModel(
    val id: Int, val title: String,
    val description: String,
    val urlToImage: String
)