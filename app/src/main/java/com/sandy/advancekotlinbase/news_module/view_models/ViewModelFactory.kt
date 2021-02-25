package com.sandy.advancekotlinbase.news_module.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sandy.advancekotlinbase.news_module.network.NewsRemoteDataRepository

class ViewModelFactory(
    private var newsRepository: NewsRemoteDataRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}