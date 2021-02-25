package com.sandy.advancekotlinbase.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sandy.advancekotlinbase.R
import com.sandy.advancekotlinbase.models.NewsMainModel
import com.sandy.advancekotlinbase.models.NewsRequestModel
import com.sandy.advancekotlinbase.network.NewsApi
import com.sandy.advancekotlinbase.utility.Resource
import kotlinx.coroutines.Dispatchers

class NewsViewModel(
    private var newsApi: NewsApi
) : ViewModel() {

    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    val newsListAdapter: NewsListAdapter = NewsListAdapter()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    fun getTopHeadlines(newsRequestModel: NewsRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            emit(
                Resource.success(
                    data = newsApi.getTopHeadlinesSuspended(
                        newsRequestModel.country,
                        newsRequestModel.category,
                        newsRequestModel.apiKey
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
    }

    fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
    }

    fun onRetrieveSuccess(newsMainModel: NewsMainModel) {
        Log.i("newsMainModel", "newsMainModel::${newsMainModel.status}")
        newsListAdapter.updateNewsList(newsMainModel.articles)
    }

    fun onRetrieveError() {
        errorMessage.value = R.string.api_news_error
    }
}