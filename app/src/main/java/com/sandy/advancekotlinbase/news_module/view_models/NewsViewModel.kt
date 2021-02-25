package com.sandy.advancekotlinbase.news_module.view_models

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sandy.advancekotlinbase.R
import com.sandy.advancekotlinbase.base_classes.BaseViewModel
import com.sandy.advancekotlinbase.news_module.models.NewsMainModel
import com.sandy.advancekotlinbase.news_module.models.NewsRequestModel
import com.sandy.advancekotlinbase.news_module.network.NewsRemoteDataRepository
import com.sandy.advancekotlinbase.news_module.ui.NewsListAdapter
import com.sandy.advancekotlinbase.utility.APIState
import kotlinx.coroutines.Dispatchers

class NewsViewModel(
    private var newsRepository: NewsRemoteDataRepository
) : BaseViewModel() {

    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    val newsListAdapter: NewsListAdapter = NewsListAdapter()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    fun getTopHeadlines(newsRequestModel: NewsRequestModel) = liveData(Dispatchers.IO) {
        emit(APIState.loading())
        try {
            emit(
                APIState.success(
                    data = newsRepository.getTopHeadlinesSuspended(
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
        newsListAdapter.updateNewsList(newsMainModel.articles)
    }

    fun onRetrieveError() {
        errorMessage.value = R.string.api_news_error
    }
}