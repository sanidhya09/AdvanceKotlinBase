package com.sandy.advancekotlinbase.news_module.view_models

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sandy.advancekotlinbase.base_classes.BaseViewModel
import com.sandy.advancekotlinbase.news_module.models.NewsMainModel
import com.sandy.advancekotlinbase.news_module.models.request_models.NewsRequestModel
import com.sandy.advancekotlinbase.news_module.network.NewsRemoteDataRepository
import com.sandy.advancekotlinbase.news_module.ui.NewsListAdapter
import com.sandy.advancekotlinbase.utility.APIState
import kotlinx.coroutines.Dispatchers

class NewsViewModel(
    private var newsRepository: NewsRemoteDataRepository
) : BaseViewModel() {

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val newsListAdapter: NewsListAdapter = NewsListAdapter()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    fun getTopHeadlines(newsRequestModel: NewsRequestModel) = liveData(Dispatchers.IO) {
        emit(APIState.loading())
        try {
            emit(
                APIState.success(
                    data = newsRepository.getTopHeadlines(
                        newsRequestModel.country,
                        newsRequestModel.category,
                        newsRequestModel.apiKey
                    )
                )
            )
        } catch (exception: Exception) {
            emit(APIState.error(data = null, message = exception.message ?: "Error Occurred!"))
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
        if (newsMainModel.articles.isNotEmpty())
            newsListAdapter.updateNewsList(newsMainModel.articles)
        else
            onRetrieveError("Something went wrong!!")
    }

    fun onRetrieveError(message: String) {
        errorMessage.value = message
    }
}