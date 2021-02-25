package com.sandy.advancekotlinbase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sandy.advancekotlinbase.network.NewsApiService

class ViewModelFactory(
    private var newsApiService: NewsApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsApiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}