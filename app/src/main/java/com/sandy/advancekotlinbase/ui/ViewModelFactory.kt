package com.sandy.advancekotlinbase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sandy.advancekotlinbase.network.NewsApi

class ViewModelFactory(
    private var newsApi: NewsApi
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}