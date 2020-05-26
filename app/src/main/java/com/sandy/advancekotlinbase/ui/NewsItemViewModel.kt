package com.sandy.advancekotlinbase.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandy.advancekotlinbase.models.NewsArticlesModel

class NewsItemViewModel : ViewModel() {
    private val newsTitle = MutableLiveData<String>()
    private val newsBody = MutableLiveData<String>()

    fun bind(model: NewsArticlesModel) {
        newsTitle.value = model.title
        newsBody.value = model.description
    }

    fun getNewsTitle(): MutableLiveData<String> {
        return newsTitle
    }

    fun getNewsBody(): MutableLiveData<String> {
        return newsBody
    }
}