package com.sandy.advancekotlinbase.news_module.view_models

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
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
        errorMessage.postValue(message)
    }

    private val srpLiveData = MutableLiveData<NewsRequestModel>()

    val observeSrpLiveData = srpLiveData.switchMap {
        liveData(context = coroutineScope.coroutineContext + Dispatchers.IO) {
            emit(
                APIState.success(
                    data = newsRepository.getTopHeadlines(
                        it.country,
                        it.category,
                        it.apiKey
                    )
                )
            )
        }
    }

    fun getTopHeadlines(url: NewsRequestModel) {
        srpLiveData.value = url
    }
}