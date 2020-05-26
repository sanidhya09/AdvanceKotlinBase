package com.sandy.advancekotlinbase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sandy.advancekotlinbase.models.NewsRequestModel
import com.sandy.advancekotlinbase.network.NewsApi

class ViewModelFactory(
    private var newsApi: NewsApi,
    private var newsRequestModel: NewsRequestModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsApi, newsRequestModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}